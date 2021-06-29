import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAdjudicationItem } from 'app/shared/model/adjudication-item.model';
import { getEntities as getAdjudicationItems } from 'app/entities/adjudication-item/adjudication-item.reducer';
import { IAdjudicationDetailItem } from 'app/shared/model/adjudication-detail-item.model';
import { getEntities as getAdjudicationDetailItems } from 'app/entities/adjudication-detail-item/adjudication-detail-item.reducer';
import { IAdjudicationSubDetailItem } from 'app/shared/model/adjudication-sub-detail-item.model';
import { getEntities as getAdjudicationSubDetailItems } from 'app/entities/adjudication-sub-detail-item/adjudication-sub-detail-item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './adjudication.reducer';
import { IAdjudication } from 'app/shared/model/adjudication.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AdjudicationUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const adjudicationItems = useAppSelector(state => state.adjudicationItem.entities);
  const adjudicationDetailItems = useAppSelector(state => state.adjudicationDetailItem.entities);
  const adjudicationSubDetailItems = useAppSelector(state => state.adjudicationSubDetailItem.entities);
  const adjudicationEntity = useAppSelector(state => state.adjudication.entity);
  const loading = useAppSelector(state => state.adjudication.loading);
  const updating = useAppSelector(state => state.adjudication.updating);
  const updateSuccess = useAppSelector(state => state.adjudication.updateSuccess);

  const handleClose = () => {
    props.history.push('/adjudication');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAdjudicationItems({}));
    dispatch(getAdjudicationDetailItems({}));
    dispatch(getAdjudicationSubDetailItems({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...adjudicationEntity,
      ...values,
      adjudicationItem: adjudicationItems.find(it => it.id.toString() === values.adjudicationItemId.toString()),
      adjudicationDetailItem: adjudicationDetailItems.find(it => it.id.toString() === values.adjudicationDetailItemId.toString()),
      adjudicationSubDetailItem: adjudicationSubDetailItems.find(it => it.id.toString() === values.adjudicationSubDetailItemId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...adjudicationEntity,
          adjudicationItemId: adjudicationEntity?.adjudicationItem?.id,
          adjudicationDetailItemId: adjudicationEntity?.adjudicationDetailItem?.id,
          adjudicationSubDetailItemId: adjudicationEntity?.adjudicationSubDetailItem?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.adjudication.home.createOrEditLabel" data-cy="AdjudicationCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.adjudication.home.createOrEditLabel">Create or edit a Adjudication</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="adjudication-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.adjudication.category')}
                id="adjudication-category"
                name="category"
                data-cy="category"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.adjudication.reason')}
                id="adjudication-reason"
                name="reason"
                data-cy="reason"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.adjudication.amount')}
                id="adjudication-amount"
                name="amount"
                data-cy="amount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.adjudication.value')}
                id="adjudication-value"
                name="value"
                data-cy="value"
                type="text"
              />
              <ValidatedField
                id="adjudication-adjudicationItem"
                name="adjudicationItemId"
                data-cy="adjudicationItem"
                label={translate('hcpNphiesPortalApp.adjudication.adjudicationItem')}
                type="select"
              >
                <option value="" key="0" />
                {adjudicationItems
                  ? adjudicationItems.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="adjudication-adjudicationDetailItem"
                name="adjudicationDetailItemId"
                data-cy="adjudicationDetailItem"
                label={translate('hcpNphiesPortalApp.adjudication.adjudicationDetailItem')}
                type="select"
              >
                <option value="" key="0" />
                {adjudicationDetailItems
                  ? adjudicationDetailItems.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="adjudication-adjudicationSubDetailItem"
                name="adjudicationSubDetailItemId"
                data-cy="adjudicationSubDetailItem"
                label={translate('hcpNphiesPortalApp.adjudication.adjudicationSubDetailItem')}
                type="select"
              >
                <option value="" key="0" />
                {adjudicationSubDetailItems
                  ? adjudicationSubDetailItems.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/adjudication" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AdjudicationUpdate;
