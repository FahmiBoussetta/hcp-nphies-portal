import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAdjudicationDetailItem } from 'app/shared/model/adjudication-detail-item.model';
import { getEntities as getAdjudicationDetailItems } from 'app/entities/adjudication-detail-item/adjudication-detail-item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './adjudication-sub-detail-item.reducer';
import { IAdjudicationSubDetailItem } from 'app/shared/model/adjudication-sub-detail-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AdjudicationSubDetailItemUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const adjudicationDetailItems = useAppSelector(state => state.adjudicationDetailItem.entities);
  const adjudicationSubDetailItemEntity = useAppSelector(state => state.adjudicationSubDetailItem.entity);
  const loading = useAppSelector(state => state.adjudicationSubDetailItem.loading);
  const updating = useAppSelector(state => state.adjudicationSubDetailItem.updating);
  const updateSuccess = useAppSelector(state => state.adjudicationSubDetailItem.updateSuccess);

  const handleClose = () => {
    props.history.push('/adjudication-sub-detail-item');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAdjudicationDetailItems({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...adjudicationSubDetailItemEntity,
      ...values,
      adjudicationDetailItem: adjudicationDetailItems.find(it => it.id.toString() === values.adjudicationDetailItemId.toString()),
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
          ...adjudicationSubDetailItemEntity,
          adjudicationDetailItemId: adjudicationSubDetailItemEntity?.adjudicationDetailItem?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="hcpNphiesPortalApp.adjudicationSubDetailItem.home.createOrEditLabel"
            data-cy="AdjudicationSubDetailItemCreateUpdateHeading"
          >
            <Translate contentKey="hcpNphiesPortalApp.adjudicationSubDetailItem.home.createOrEditLabel">
              Create or edit a AdjudicationSubDetailItem
            </Translate>
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
                  id="adjudication-sub-detail-item-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.adjudicationSubDetailItem.sequence')}
                id="adjudication-sub-detail-item-sequence"
                name="sequence"
                data-cy="sequence"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="adjudication-sub-detail-item-adjudicationDetailItem"
                name="adjudicationDetailItemId"
                data-cy="adjudicationDetailItem"
                label={translate('hcpNphiesPortalApp.adjudicationSubDetailItem.adjudicationDetailItem')}
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
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/adjudication-sub-detail-item"
                replace
                color="info"
              >
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

export default AdjudicationSubDetailItemUpdate;
