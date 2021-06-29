import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPatient } from 'app/shared/model/patient.model';
import { getEntities as getPatients } from 'app/entities/patient/patient.reducer';
import { IOrganization } from 'app/shared/model/organization.model';
import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { ILocation } from 'app/shared/model/location.model';
import { getEntities as getLocations } from 'app/entities/location/location.reducer';
import { ICoverage } from 'app/shared/model/coverage.model';
import { getEntities as getCoverages } from 'app/entities/coverage/coverage.reducer';
import { getEntity, updateEntity, createEntity, reset } from './coverage-eligibility-request.reducer';
import { ICoverageEligibilityRequest } from 'app/shared/model/coverage-eligibility-request.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CoverageEligibilityRequestUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const patients = useAppSelector(state => state.patient.entities);
  const organizations = useAppSelector(state => state.organization.entities);
  const locations = useAppSelector(state => state.location.entities);
  const coverages = useAppSelector(state => state.coverage.entities);
  const coverageEligibilityRequestEntity = useAppSelector(state => state.coverageEligibilityRequest.entity);
  const loading = useAppSelector(state => state.coverageEligibilityRequest.loading);
  const updating = useAppSelector(state => state.coverageEligibilityRequest.updating);
  const updateSuccess = useAppSelector(state => state.coverageEligibilityRequest.updateSuccess);

  const handleClose = () => {
    props.history.push('/coverage-eligibility-request');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPatients({}));
    dispatch(getOrganizations({}));
    dispatch(getLocations({}));
    dispatch(getCoverages({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.servicedDate = convertDateTimeToServer(values.servicedDate);
    values.servicedDateEnd = convertDateTimeToServer(values.servicedDateEnd);

    const entity = {
      ...coverageEligibilityRequestEntity,
      ...values,
      coverages: mapIdList(values.coverages),
      patient: patients.find(it => it.id.toString() === values.patientId.toString()),
      provider: organizations.find(it => it.id.toString() === values.providerId.toString()),
      insurer: organizations.find(it => it.id.toString() === values.insurerId.toString()),
      facility: locations.find(it => it.id.toString() === values.facilityId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          servicedDate: displayDefaultDateTime(),
          servicedDateEnd: displayDefaultDateTime(),
        }
      : {
          ...coverageEligibilityRequestEntity,
          priority: 'Stat',
          servicedDate: convertDateTimeFromServer(coverageEligibilityRequestEntity.servicedDate),
          servicedDateEnd: convertDateTimeFromServer(coverageEligibilityRequestEntity.servicedDateEnd),
          patientId: coverageEligibilityRequestEntity?.patient?.id,
          providerId: coverageEligibilityRequestEntity?.provider?.id,
          insurerId: coverageEligibilityRequestEntity?.insurer?.id,
          facilityId: coverageEligibilityRequestEntity?.facility?.id,
          coverages: coverageEligibilityRequestEntity?.coverages?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="hcpNphiesPortalApp.coverageEligibilityRequest.home.createOrEditLabel"
            data-cy="CoverageEligibilityRequestCreateUpdateHeading"
          >
            <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.home.createOrEditLabel">
              Create or edit a CoverageEligibilityRequest
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
                  id="coverage-eligibility-request-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverageEligibilityRequest.guid')}
                id="coverage-eligibility-request-guid"
                name="guid"
                data-cy="guid"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverageEligibilityRequest.parsed')}
                id="coverage-eligibility-request-parsed"
                name="parsed"
                data-cy="parsed"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverageEligibilityRequest.priority')}
                id="coverage-eligibility-request-priority"
                name="priority"
                data-cy="priority"
                type="select"
              >
                <option value="Stat">{translate('hcpNphiesPortalApp.PriorityEnum.Stat')}</option>
                <option value="Normal">{translate('hcpNphiesPortalApp.PriorityEnum.Normal')}</option>
                <option value="Deferred">{translate('hcpNphiesPortalApp.PriorityEnum.Deferred')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverageEligibilityRequest.identifier')}
                id="coverage-eligibility-request-identifier"
                name="identifier"
                data-cy="identifier"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverageEligibilityRequest.servicedDate')}
                id="coverage-eligibility-request-servicedDate"
                name="servicedDate"
                data-cy="servicedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverageEligibilityRequest.servicedDateEnd')}
                id="coverage-eligibility-request-servicedDateEnd"
                name="servicedDateEnd"
                data-cy="servicedDateEnd"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="coverage-eligibility-request-patient"
                name="patientId"
                data-cy="patient"
                label={translate('hcpNphiesPortalApp.coverageEligibilityRequest.patient')}
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
                id="coverage-eligibility-request-provider"
                name="providerId"
                data-cy="provider"
                label={translate('hcpNphiesPortalApp.coverageEligibilityRequest.provider')}
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
              <ValidatedField
                id="coverage-eligibility-request-insurer"
                name="insurerId"
                data-cy="insurer"
                label={translate('hcpNphiesPortalApp.coverageEligibilityRequest.insurer')}
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
              <ValidatedField
                id="coverage-eligibility-request-facility"
                name="facilityId"
                data-cy="facility"
                label={translate('hcpNphiesPortalApp.coverageEligibilityRequest.facility')}
                type="select"
              >
                <option value="" key="0" />
                {locations
                  ? locations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverageEligibilityRequest.coverages')}
                id="coverage-eligibility-request-coverages"
                data-cy="coverages"
                type="select"
                multiple
                name="coverages"
              >
                <option value="" key="0" />
                {coverages
                  ? coverages.map(otherEntity => (
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
                to="/coverage-eligibility-request"
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

export default CoverageEligibilityRequestUpdate;
