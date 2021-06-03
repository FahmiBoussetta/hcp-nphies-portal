import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPatient } from 'app/shared/model/patient.model';
import { getEntities as getPatients } from 'app/entities/patient/patient.reducer';
import { IOrganization } from 'app/shared/model/organization.model';
import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { ILocation } from 'app/shared/model/location.model';
import { getEntities as getLocations } from 'app/entities/location/location.reducer';
import { getEntity, updateEntity, createEntity, reset } from './coverage-eligibility-request.reducer';
import { ICoverageEligibilityRequest } from 'app/shared/model/coverage-eligibility-request.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICoverageEligibilityRequestUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CoverageEligibilityRequestUpdate = (props: ICoverageEligibilityRequestUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { coverageEligibilityRequestEntity, patients, organizations, locations, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/coverage-eligibility-request');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPatients();
    props.getOrganizations();
    props.getLocations();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.servicedDate = convertDateTimeToServer(values.servicedDate);
    values.servicedDateEnd = convertDateTimeToServer(values.servicedDateEnd);

    if (errors.length === 0) {
      const entity = {
        ...coverageEligibilityRequestEntity,
        ...values,
        patient: patients.find(it => it.id.toString() === values.patientId.toString()),
        provider: organizations.find(it => it.id.toString() === values.providerId.toString()),
        insurer: organizations.find(it => it.id.toString() === values.insurerId.toString()),
        facility: locations.find(it => it.id.toString() === values.facilityId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
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
            <AvForm model={isNew ? {} : coverageEligibilityRequestEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="coverage-eligibility-request-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="coverage-eligibility-request-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="guidLabel" for="coverage-eligibility-request-guid">
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.guid">Guid</Translate>
                </Label>
                <AvField id="coverage-eligibility-request-guid" data-cy="guid" type="text" name="guid" />
              </AvGroup>
              <AvGroup>
                <Label id="parsedLabel" for="coverage-eligibility-request-parsed">
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.parsed">Parsed</Translate>
                </Label>
                <AvField id="coverage-eligibility-request-parsed" data-cy="parsed" type="text" name="parsed" />
              </AvGroup>
              <AvGroup>
                <Label id="priorityLabel" for="coverage-eligibility-request-priority">
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.priority">Priority</Translate>
                </Label>
                <AvInput
                  id="coverage-eligibility-request-priority"
                  data-cy="priority"
                  type="select"
                  className="form-control"
                  name="priority"
                  value={(!isNew && coverageEligibilityRequestEntity.priority) || 'Stat'}
                >
                  <option value="Stat">{translate('hcpNphiesPortalApp.PriorityEnum.Stat')}</option>
                  <option value="Normal">{translate('hcpNphiesPortalApp.PriorityEnum.Normal')}</option>
                  <option value="Deferred">{translate('hcpNphiesPortalApp.PriorityEnum.Deferred')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="identifierLabel" for="coverage-eligibility-request-identifier">
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.identifier">Identifier</Translate>
                </Label>
                <AvField id="coverage-eligibility-request-identifier" data-cy="identifier" type="text" name="identifier" />
              </AvGroup>
              <AvGroup>
                <Label id="servicedDateLabel" for="coverage-eligibility-request-servicedDate">
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.servicedDate">Serviced Date</Translate>
                </Label>
                <AvInput
                  id="coverage-eligibility-request-servicedDate"
                  data-cy="servicedDate"
                  type="datetime-local"
                  className="form-control"
                  name="servicedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.coverageEligibilityRequestEntity.servicedDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="servicedDateEndLabel" for="coverage-eligibility-request-servicedDateEnd">
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.servicedDateEnd">Serviced Date End</Translate>
                </Label>
                <AvInput
                  id="coverage-eligibility-request-servicedDateEnd"
                  data-cy="servicedDateEnd"
                  type="datetime-local"
                  className="form-control"
                  name="servicedDateEnd"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={
                    isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.coverageEligibilityRequestEntity.servicedDateEnd)
                  }
                />
              </AvGroup>
              <AvGroup>
                <Label for="coverage-eligibility-request-patient">
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.patient">Patient</Translate>
                </Label>
                <AvInput
                  id="coverage-eligibility-request-patient"
                  data-cy="patient"
                  type="select"
                  className="form-control"
                  name="patientId"
                >
                  <option value="" key="0" />
                  {patients
                    ? patients.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="coverage-eligibility-request-provider">
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.provider">Provider</Translate>
                </Label>
                <AvInput
                  id="coverage-eligibility-request-provider"
                  data-cy="provider"
                  type="select"
                  className="form-control"
                  name="providerId"
                >
                  <option value="" key="0" />
                  {organizations
                    ? organizations.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="coverage-eligibility-request-insurer">
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.insurer">Insurer</Translate>
                </Label>
                <AvInput
                  id="coverage-eligibility-request-insurer"
                  data-cy="insurer"
                  type="select"
                  className="form-control"
                  name="insurerId"
                >
                  <option value="" key="0" />
                  {organizations
                    ? organizations.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="coverage-eligibility-request-facility">
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityRequest.facility">Facility</Translate>
                </Label>
                <AvInput
                  id="coverage-eligibility-request-facility"
                  data-cy="facility"
                  type="select"
                  className="form-control"
                  name="facilityId"
                >
                  <option value="" key="0" />
                  {locations
                    ? locations.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/coverage-eligibility-request" replace color="info">
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
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  patients: storeState.patient.entities,
  organizations: storeState.organization.entities,
  locations: storeState.location.entities,
  coverageEligibilityRequestEntity: storeState.coverageEligibilityRequest.entity,
  loading: storeState.coverageEligibilityRequest.loading,
  updating: storeState.coverageEligibilityRequest.updating,
  updateSuccess: storeState.coverageEligibilityRequest.updateSuccess,
});

const mapDispatchToProps = {
  getPatients,
  getOrganizations,
  getLocations,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CoverageEligibilityRequestUpdate);
