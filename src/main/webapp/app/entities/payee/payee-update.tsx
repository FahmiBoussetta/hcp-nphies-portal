import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPatient } from 'app/shared/model/patient.model';
import { getEntities as getPatients } from 'app/entities/patient/patient.reducer';
import { IOrganization } from 'app/shared/model/organization.model';
import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { getEntity, updateEntity, createEntity, reset } from './payee.reducer';
import { IPayee } from 'app/shared/model/payee.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PayeeUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const patients = useAppSelector(state => state.patient.entities);
  const organizations = useAppSelector(state => state.organization.entities);
  const payeeEntity = useAppSelector(state => state.payee.entity);
  const loading = useAppSelector(state => state.payee.loading);
  const updating = useAppSelector(state => state.payee.updating);
  const updateSuccess = useAppSelector(state => state.payee.updateSuccess);

  const handleClose = () => {
    props.history.push('/payee');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPatients({}));
    dispatch(getOrganizations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...payeeEntity,
      ...values,
      partyPatient: patients.find(it => it.id.toString() === values.partyPatientId.toString()),
      partyOrganization: organizations.find(it => it.id.toString() === values.partyOrganizationId.toString()),
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
          ...payeeEntity,
          type: 'Subscriber',
          partyPatientId: payeeEntity?.partyPatient?.id,
          partyOrganizationId: payeeEntity?.partyOrganization?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.payee.home.createOrEditLabel" data-cy="PayeeCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.payee.home.createOrEditLabel">Create or edit a Payee</Translate>
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
                  id="payee-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('hcpNphiesPortalApp.payee.type')} id="payee-type" name="type" data-cy="type" type="select">
                <option value="Subscriber">{translate('hcpNphiesPortalApp.PayeeTypeEnum.Subscriber')}</option>
                <option value="Provider">{translate('hcpNphiesPortalApp.PayeeTypeEnum.Provider')}</option>
                <option value="Other">{translate('hcpNphiesPortalApp.PayeeTypeEnum.Other')}</option>
              </ValidatedField>
              <ValidatedField
                id="payee-partyPatient"
                name="partyPatientId"
                data-cy="partyPatient"
                label={translate('hcpNphiesPortalApp.payee.partyPatient')}
                type="select"
              >
                <option value="" key="0" />
                {patients
                  ? patients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="payee-partyOrganization"
                name="partyOrganizationId"
                data-cy="partyOrganization"
                label={translate('hcpNphiesPortalApp.payee.partyOrganization')}
                type="select"
              >
                <option value="" key="0" />
                {organizations
                  ? organizations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/payee" replace color="info">
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

export default PayeeUpdate;
