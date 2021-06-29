import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IResponseInsuranceItem } from 'app/shared/model/response-insurance-item.model';
import { getEntities as getResponseInsuranceItems } from 'app/entities/response-insurance-item/response-insurance-item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './insurance-benefit.reducer';
import { IInsuranceBenefit } from 'app/shared/model/insurance-benefit.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const InsuranceBenefitUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const responseInsuranceItems = useAppSelector(state => state.responseInsuranceItem.entities);
  const insuranceBenefitEntity = useAppSelector(state => state.insuranceBenefit.entity);
  const loading = useAppSelector(state => state.insuranceBenefit.loading);
  const updating = useAppSelector(state => state.insuranceBenefit.updating);
  const updateSuccess = useAppSelector(state => state.insuranceBenefit.updateSuccess);

  const handleClose = () => {
    props.history.push('/insurance-benefit');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getResponseInsuranceItems({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...insuranceBenefitEntity,
      ...values,
      responseInsuranceItem: responseInsuranceItems.find(it => it.id.toString() === values.responseInsuranceItemId.toString()),
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
          ...insuranceBenefitEntity,
          responseInsuranceItemId: insuranceBenefitEntity?.responseInsuranceItem?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.insuranceBenefit.home.createOrEditLabel" data-cy="InsuranceBenefitCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.insuranceBenefit.home.createOrEditLabel">Create or edit a InsuranceBenefit</Translate>
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
                  id="insurance-benefit-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.insuranceBenefit.allowed')}
                id="insurance-benefit-allowed"
                name="allowed"
                data-cy="allowed"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.insuranceBenefit.used')}
                id="insurance-benefit-used"
                name="used"
                data-cy="used"
                type="text"
              />
              <ValidatedField
                id="insurance-benefit-responseInsuranceItem"
                name="responseInsuranceItemId"
                data-cy="responseInsuranceItem"
                label={translate('hcpNphiesPortalApp.insuranceBenefit.responseInsuranceItem')}
                type="select"
              >
                <option value="" key="0" />
                {responseInsuranceItems
                  ? responseInsuranceItems.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/insurance-benefit" replace color="info">
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

export default InsuranceBenefitUpdate;
