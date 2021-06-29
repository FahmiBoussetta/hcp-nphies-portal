import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAdjudicationItem } from 'app/shared/model/adjudication-item.model';
import { getEntities as getAdjudicationItems } from 'app/entities/adjudication-item/adjudication-item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './adjudication-detail-item.reducer';
import { IAdjudicationDetailItem } from 'app/shared/model/adjudication-detail-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AdjudicationDetailItemUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const adjudicationItems = useAppSelector(state => state.adjudicationItem.entities);
  const adjudicationDetailItemEntity = useAppSelector(state => state.adjudicationDetailItem.entity);
  const loading = useAppSelector(state => state.adjudicationDetailItem.loading);
  const updating = useAppSelector(state => state.adjudicationDetailItem.updating);
  const updateSuccess = useAppSelector(state => state.adjudicationDetailItem.updateSuccess);

  const handleClose = () => {
    props.history.push('/adjudication-detail-item');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAdjudicationItems({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...adjudicationDetailItemEntity,
      ...values,
      adjudicationItem: adjudicationItems.find(it => it.id.toString() === values.adjudicationItemId.toString()),
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
          ...adjudicationDetailItemEntity,
          adjudicationItemId: adjudicationDetailItemEntity?.adjudicationItem?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.adjudicationDetailItem.home.createOrEditLabel" data-cy="AdjudicationDetailItemCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.adjudicationDetailItem.home.createOrEditLabel">
              Create or edit a AdjudicationDetailItem
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
                  id="adjudication-detail-item-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.adjudicationDetailItem.sequence')}
                id="adjudication-detail-item-sequence"
                name="sequence"
                data-cy="sequence"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="adjudication-detail-item-adjudicationItem"
                name="adjudicationItemId"
                data-cy="adjudicationItem"
                label={translate('hcpNphiesPortalApp.adjudicationDetailItem.adjudicationItem')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/adjudication-detail-item" replace color="info">
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

export default AdjudicationDetailItemUpdate;
