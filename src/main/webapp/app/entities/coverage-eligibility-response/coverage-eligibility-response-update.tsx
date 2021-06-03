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
import { getEntity, updateEntity, createEntity, reset } from './coverage-eligibility-response.reducer';
import { ICoverageEligibilityResponse } from 'app/shared/model/coverage-eligibility-response.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICoverageEligibilityResponseUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CoverageEligibilityResponseUpdate = (props: ICoverageEligibilityResponseUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { coverageEligibilityResponseEntity, patients, organizations, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/coverage-eligibility-response');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPatients();
    props.getOrganizations();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.serviced = convertDateTimeToServer(values.serviced);
    values.servicedEnd = convertDateTimeToServer(values.servicedEnd);

    if (errors.length === 0) {
      const entity = {
        ...coverageEligibilityResponseEntity,
        ...values,
        patient: patients.find(it => it.id.toString() === values.patientId.toString()),
        insurer: organizations.find(it => it.id.toString() === values.insurerId.toString()),
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
            <AvForm model={isNew ? {} : coverageEligibilityResponseEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="coverage-eligibility-response-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="coverage-eligibility-response-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="valueLabel" for="coverage-eligibility-response-value">
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.value">Value</Translate>
                </Label>
                <AvField id="coverage-eligibility-response-value" data-cy="value" type="text" name="value" />
              </AvGroup>
              <AvGroup>
                <Label id="systemLabel" for="coverage-eligibility-response-system">
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.system">System</Translate>
                </Label>
                <AvField id="coverage-eligibility-response-system" data-cy="system" type="text" name="system" />
              </AvGroup>
              <AvGroup>
                <Label id="parsedLabel" for="coverage-eligibility-response-parsed">
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.parsed">Parsed</Translate>
                </Label>
                <AvField id="coverage-eligibility-response-parsed" data-cy="parsed" type="text" name="parsed" />
              </AvGroup>
              <AvGroup>
                <Label id="outcomeLabel" for="coverage-eligibility-response-outcome">
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.outcome">Outcome</Translate>
                </Label>
                <AvField id="coverage-eligibility-response-outcome" data-cy="outcome" type="text" name="outcome" />
              </AvGroup>
              <AvGroup>
                <Label id="servicedLabel" for="coverage-eligibility-response-serviced">
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.serviced">Serviced</Translate>
                </Label>
                <AvInput
                  id="coverage-eligibility-response-serviced"
                  data-cy="serviced"
                  type="datetime-local"
                  className="form-control"
                  name="serviced"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.coverageEligibilityResponseEntity.serviced)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="servicedEndLabel" for="coverage-eligibility-response-servicedEnd">
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.servicedEnd">Serviced End</Translate>
                </Label>
                <AvInput
                  id="coverage-eligibility-response-servicedEnd"
                  data-cy="servicedEnd"
                  type="datetime-local"
                  className="form-control"
                  name="servicedEnd"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.coverageEligibilityResponseEntity.servicedEnd)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dispositionLabel" for="coverage-eligibility-response-disposition">
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.disposition">Disposition</Translate>
                </Label>
                <AvField id="coverage-eligibility-response-disposition" data-cy="disposition" type="text" name="disposition" />
              </AvGroup>
              <AvGroup>
                <Label id="notInforceReasonLabel" for="coverage-eligibility-response-notInforceReason">
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.notInforceReason">Not Inforce Reason</Translate>
                </Label>
                <AvField
                  id="coverage-eligibility-response-notInforceReason"
                  data-cy="notInforceReason"
                  type="text"
                  name="notInforceReason"
                />
              </AvGroup>
              <AvGroup>
                <Label for="coverage-eligibility-response-patient">
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.patient">Patient</Translate>
                </Label>
                <AvInput
                  id="coverage-eligibility-response-patient"
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
                <Label for="coverage-eligibility-response-insurer">
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.insurer">Insurer</Translate>
                </Label>
                <AvInput
                  id="coverage-eligibility-response-insurer"
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
              <Button tag={Link} id="cancel-save" to="/coverage-eligibility-response" replace color="info">
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
  coverageEligibilityResponseEntity: storeState.coverageEligibilityResponse.entity,
  loading: storeState.coverageEligibilityResponse.loading,
  updating: storeState.coverageEligibilityResponse.updating,
  updateSuccess: storeState.coverageEligibilityResponse.updateSuccess,
});

const mapDispatchToProps = {
  getPatients,
  getOrganizations,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CoverageEligibilityResponseUpdate);
