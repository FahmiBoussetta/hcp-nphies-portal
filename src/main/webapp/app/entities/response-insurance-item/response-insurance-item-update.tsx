import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IResponseInsurance } from 'app/shared/model/response-insurance.model';
import { getEntities as getResponseInsurances } from 'app/entities/response-insurance/response-insurance.reducer';
import { getEntity, updateEntity, createEntity, reset } from './response-insurance-item.reducer';
import { IResponseInsuranceItem } from 'app/shared/model/response-insurance-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ResponseInsuranceItemUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const responseInsurances = useAppSelector(state => state.responseInsurance.entities);
  const responseInsuranceItemEntity = useAppSelector(state => state.responseInsuranceItem.entity);
  const loading = useAppSelector(state => state.responseInsuranceItem.loading);
  const updating = useAppSelector(state => state.responseInsuranceItem.updating);
  const updateSuccess = useAppSelector(state => state.responseInsuranceItem.updateSuccess);

  const handleClose = () => {
    props.history.push('/response-insurance-item');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getResponseInsurances({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...responseInsuranceItemEntity,
      ...values,
      responseInsurance: responseInsurances.find(it => it.id.toString() === values.responseInsuranceId.toString()),
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
          ...responseInsuranceItemEntity,
          responseInsuranceId: responseInsuranceItemEntity?.responseInsurance?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.responseInsuranceItem.home.createOrEditLabel" data-cy="ResponseInsuranceItemCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.home.createOrEditLabel">
              Create or edit a ResponseInsuranceItem
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
                  id="response-insurance-item-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.responseInsuranceItem.category')}
                id="response-insurance-item-category"
                name="category"
                data-cy="category"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.responseInsuranceItem.excluded')}
                id="response-insurance-item-excluded"
                name="excluded"
                data-cy="excluded"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.responseInsuranceItem.name')}
                id="response-insurance-item-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.responseInsuranceItem.description')}
                id="response-insurance-item-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.responseInsuranceItem.network')}
                id="response-insurance-item-network"
                name="network"
                data-cy="network"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.responseInsuranceItem.unit')}
                id="response-insurance-item-unit"
                name="unit"
                data-cy="unit"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.responseInsuranceItem.term')}
                id="response-insurance-item-term"
                name="term"
                data-cy="term"
                type="text"
              />
              <ValidatedField
                id="response-insurance-item-responseInsurance"
                name="responseInsuranceId"
                data-cy="responseInsurance"
                label={translate('hcpNphiesPortalApp.responseInsuranceItem.responseInsurance')}
                type="select"
              >
                <option value="" key="0" />
                {responseInsurances
                  ? responseInsurances.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/response-insurance-item" replace color="info">
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

export default ResponseInsuranceItemUpdate;
