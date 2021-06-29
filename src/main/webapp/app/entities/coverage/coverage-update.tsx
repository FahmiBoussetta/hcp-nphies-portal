import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPatient } from 'app/shared/model/patient.model';
import { getEntities as getPatients } from 'app/entities/patient/patient.reducer';
import { IOrganization } from 'app/shared/model/organization.model';
import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { ICoverageEligibilityRequest } from 'app/shared/model/coverage-eligibility-request.model';
import { getEntities as getCoverageEligibilityRequests } from 'app/entities/coverage-eligibility-request/coverage-eligibility-request.reducer';
import { getEntity, updateEntity, createEntity, reset } from './coverage.reducer';
import { ICoverage } from 'app/shared/model/coverage.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CoverageUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const patients = useAppSelector(state => state.patient.entities);
  const organizations = useAppSelector(state => state.organization.entities);
  const coverageEligibilityRequests = useAppSelector(state => state.coverageEligibilityRequest.entities);
  const coverageEntity = useAppSelector(state => state.coverage.entity);
  const loading = useAppSelector(state => state.coverage.loading);
  const updating = useAppSelector(state => state.coverage.updating);
  const updateSuccess = useAppSelector(state => state.coverage.updateSuccess);

  const handleClose = () => {
    props.history.push('/coverage');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPatients({}));
    dispatch(getOrganizations({}));
    dispatch(getCoverageEligibilityRequests({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...coverageEntity,
      ...values,
      subscriberPatient: patients.find(it => it.id.toString() === values.subscriberPatientId.toString()),
      beneficiary: patients.find(it => it.id.toString() === values.beneficiaryId.toString()),
      payor: organizations.find(it => it.id.toString() === values.payorId.toString()),
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
          ...coverageEntity,
          coverageType: 'EHCPOL',
          relationShip: 'Child',
          subscriberPatientId: coverageEntity?.subscriberPatient?.id,
          beneficiaryId: coverageEntity?.beneficiary?.id,
          payorId: coverageEntity?.payor?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.coverage.home.createOrEditLabel" data-cy="CoverageCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.coverage.home.createOrEditLabel">Create or edit a Coverage</Translate>
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
                  id="coverage-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverage.guid')}
                id="coverage-guid"
                name="guid"
                data-cy="guid"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverage.forceId')}
                id="coverage-forceId"
                name="forceId"
                data-cy="forceId"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverage.coverageType')}
                id="coverage-coverageType"
                name="coverageType"
                data-cy="coverageType"
                type="select"
              >
                <option value="EHCPOL">{translate('hcpNphiesPortalApp.CoverageTypeEnum.EHCPOL')}</option>
                <option value="PUBLICPOL">{translate('hcpNphiesPortalApp.CoverageTypeEnum.PUBLICPOL')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverage.subscriberId')}
                id="coverage-subscriberId"
                name="subscriberId"
                data-cy="subscriberId"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverage.dependent')}
                id="coverage-dependent"
                name="dependent"
                data-cy="dependent"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverage.relationShip')}
                id="coverage-relationShip"
                name="relationShip"
                data-cy="relationShip"
                type="select"
              >
                <option value="Child">{translate('hcpNphiesPortalApp.RelationShipEnum.Child')}</option>
                <option value="Parent">{translate('hcpNphiesPortalApp.RelationShipEnum.Parent')}</option>
                <option value="Spouse">{translate('hcpNphiesPortalApp.RelationShipEnum.Spouse')}</option>
                <option value="Common">{translate('hcpNphiesPortalApp.RelationShipEnum.Common')}</option>
                <option value="Other">{translate('hcpNphiesPortalApp.RelationShipEnum.Other')}</option>
                <option value="Self">{translate('hcpNphiesPortalApp.RelationShipEnum.Self')}</option>
                <option value="Injured">{translate('hcpNphiesPortalApp.RelationShipEnum.Injured')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverage.network')}
                id="coverage-network"
                name="network"
                data-cy="network"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverage.subrogation')}
                id="coverage-subrogation"
                name="subrogation"
                data-cy="subrogation"
                check
                type="checkbox"
              />
              <ValidatedField
                id="coverage-subscriberPatient"
                name="subscriberPatientId"
                data-cy="subscriberPatient"
                label={translate('hcpNphiesPortalApp.coverage.subscriberPatient')}
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
                id="coverage-beneficiary"
                name="beneficiaryId"
                data-cy="beneficiary"
                label={translate('hcpNphiesPortalApp.coverage.beneficiary')}
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
                id="coverage-payor"
                name="payorId"
                data-cy="payor"
                label={translate('hcpNphiesPortalApp.coverage.payor')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/coverage" replace color="info">
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

export default CoverageUpdate;
