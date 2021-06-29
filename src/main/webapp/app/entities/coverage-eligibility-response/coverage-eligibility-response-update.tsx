import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPatient } from 'app/shared/model/patient.model';
import { getEntities as getPatients } from 'app/entities/patient/patient.reducer';
import { IOrganization } from 'app/shared/model/organization.model';
import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { getEntity, updateEntity, createEntity, reset } from './coverage-eligibility-response.reducer';
import { ICoverageEligibilityResponse } from 'app/shared/model/coverage-eligibility-response.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CoverageEligibilityResponseUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const patients = useAppSelector(state => state.patient.entities);
  const organizations = useAppSelector(state => state.organization.entities);
  const coverageEligibilityResponseEntity = useAppSelector(state => state.coverageEligibilityResponse.entity);
  const loading = useAppSelector(state => state.coverageEligibilityResponse.loading);
  const updating = useAppSelector(state => state.coverageEligibilityResponse.updating);
  const updateSuccess = useAppSelector(state => state.coverageEligibilityResponse.updateSuccess);

  const handleClose = () => {
    props.history.push('/coverage-eligibility-response');
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
    values.serviced = convertDateTimeToServer(values.serviced);
    values.servicedEnd = convertDateTimeToServer(values.servicedEnd);

    const entity = {
      ...coverageEligibilityResponseEntity,
      ...values,
      patient: patients.find(it => it.id.toString() === values.patientId.toString()),
      insurer: organizations.find(it => it.id.toString() === values.insurerId.toString()),
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
          serviced: displayDefaultDateTime(),
          servicedEnd: displayDefaultDateTime(),
        }
      : {
          ...coverageEligibilityResponseEntity,
          serviced: convertDateTimeFromServer(coverageEligibilityResponseEntity.serviced),
          servicedEnd: convertDateTimeFromServer(coverageEligibilityResponseEntity.servicedEnd),
          patientId: coverageEligibilityResponseEntity?.patient?.id,
          insurerId: coverageEligibilityResponseEntity?.insurer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="hcpNphiesPortalApp.coverageEligibilityResponse.home.createOrEditLabel"
            data-cy="CoverageEligibilityResponseCreateUpdateHeading"
          >
            <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.home.createOrEditLabel">
              Create or edit a CoverageEligibilityResponse
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
                  id="coverage-eligibility-response-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverageEligibilityResponse.value')}
                id="coverage-eligibility-response-value"
                name="value"
                data-cy="value"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverageEligibilityResponse.system')}
                id="coverage-eligibility-response-system"
                name="system"
                data-cy="system"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverageEligibilityResponse.parsed')}
                id="coverage-eligibility-response-parsed"
                name="parsed"
                data-cy="parsed"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverageEligibilityResponse.outcome')}
                id="coverage-eligibility-response-outcome"
                name="outcome"
                data-cy="outcome"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverageEligibilityResponse.serviced')}
                id="coverage-eligibility-response-serviced"
                name="serviced"
                data-cy="serviced"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverageEligibilityResponse.servicedEnd')}
                id="coverage-eligibility-response-servicedEnd"
                name="servicedEnd"
                data-cy="servicedEnd"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverageEligibilityResponse.disposition')}
                id="coverage-eligibility-response-disposition"
                name="disposition"
                data-cy="disposition"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.coverageEligibilityResponse.notInforceReason')}
                id="coverage-eligibility-response-notInforceReason"
                name="notInforceReason"
                data-cy="notInforceReason"
                type="text"
              />
              <ValidatedField
                id="coverage-eligibility-response-patient"
                name="patientId"
                data-cy="patient"
                label={translate('hcpNphiesPortalApp.coverageEligibilityResponse.patient')}
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
                id="coverage-eligibility-response-insurer"
                name="insurerId"
                data-cy="insurer"
                label={translate('hcpNphiesPortalApp.coverageEligibilityResponse.insurer')}
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
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/coverage-eligibility-response"
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

export default CoverageEligibilityResponseUpdate;
