import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEncounter } from 'app/shared/model/encounter.model';
import { getEntities as getEncounters } from 'app/entities/encounter/encounter.reducer';
import { ICoverageEligibilityResponse } from 'app/shared/model/coverage-eligibility-response.model';
import { getEntities as getCoverageEligibilityResponses } from 'app/entities/coverage-eligibility-response/coverage-eligibility-response.reducer';
import { IPatient } from 'app/shared/model/patient.model';
import { getEntities as getPatients } from 'app/entities/patient/patient.reducer';
import { IOrganization } from 'app/shared/model/organization.model';
import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { IReferenceIdentifier } from 'app/shared/model/reference-identifier.model';
import { getEntities as getReferenceIdentifiers } from 'app/entities/reference-identifier/reference-identifier.reducer';
import { IPayee } from 'app/shared/model/payee.model';
import { getEntities as getPayees } from 'app/entities/payee/payee.reducer';
import { ILocation } from 'app/shared/model/location.model';
import { getEntities as getLocations } from 'app/entities/location/location.reducer';
import { IAccident } from 'app/shared/model/accident.model';
import { getEntities as getAccidents } from 'app/entities/accident/accident.reducer';
import { getEntity, updateEntity, createEntity, reset } from './claim.reducer';
import { IClaim } from 'app/shared/model/claim.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClaimUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClaimUpdate = (props: IClaimUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const {
    claimEntity,
    encounters,
    coverageEligibilityResponses,
    patients,
    organizations,
    referenceIdentifiers,
    payees,
    locations,
    accidents,
    loading,
    updating,
  } = props;

  const handleClose = () => {
    props.history.push('/claim');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEncounters();
    props.getCoverageEligibilityResponses();
    props.getPatients();
    props.getOrganizations();
    props.getReferenceIdentifiers();
    props.getPayees();
    props.getLocations();
    props.getAccidents();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.eligibilityOfflineDate = convertDateTimeToServer(values.eligibilityOfflineDate);
    values.authorizationOfflineDate = convertDateTimeToServer(values.authorizationOfflineDate);
    values.billableStart = convertDateTimeToServer(values.billableStart);
    values.billableEnd = convertDateTimeToServer(values.billableEnd);

    if (errors.length === 0) {
      const entity = {
        ...claimEntity,
        ...values,
        encounter: encounters.find(it => it.id.toString() === values.encounterId.toString()),
        eligibilityResponse: coverageEligibilityResponses.find(it => it.id.toString() === values.eligibilityResponseId.toString()),
        patient: patients.find(it => it.id.toString() === values.patientId.toString()),
        provider: organizations.find(it => it.id.toString() === values.providerId.toString()),
        insurer: organizations.find(it => it.id.toString() === values.insurerId.toString()),
        prescription: referenceIdentifiers.find(it => it.id.toString() === values.prescriptionId.toString()),
        originalPrescription: referenceIdentifiers.find(it => it.id.toString() === values.originalPrescriptionId.toString()),
        referral: referenceIdentifiers.find(it => it.id.toString() === values.referralId.toString()),
        payee: payees.find(it => it.id.toString() === values.payeeId.toString()),
        facility: locations.find(it => it.id.toString() === values.facilityId.toString()),
        accident: accidents.find(it => it.id.toString() === values.accidentId.toString()),
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
          <h2 id="hcpNphiesPortalApp.claim.home.createOrEditLabel" data-cy="ClaimCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.claim.home.createOrEditLabel">Create or edit a Claim</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : claimEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="claim-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="claim-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="guidLabel" for="claim-guid">
                  <Translate contentKey="hcpNphiesPortalApp.claim.guid">Guid</Translate>
                </Label>
                <AvField id="claim-guid" data-cy="guid" type="text" name="guid" />
              </AvGroup>
              <AvGroup check>
                <Label id="isQueuedLabel">
                  <AvInput id="claim-isQueued" data-cy="isQueued" type="checkbox" className="form-check-input" name="isQueued" />
                  <Translate contentKey="hcpNphiesPortalApp.claim.isQueued">Is Queued</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="parsedLabel" for="claim-parsed">
                  <Translate contentKey="hcpNphiesPortalApp.claim.parsed">Parsed</Translate>
                </Label>
                <AvField id="claim-parsed" data-cy="parsed" type="text" name="parsed" />
              </AvGroup>
              <AvGroup>
                <Label id="identifierLabel" for="claim-identifier">
                  <Translate contentKey="hcpNphiesPortalApp.claim.identifier">Identifier</Translate>
                </Label>
                <AvField id="claim-identifier" data-cy="identifier" type="text" name="identifier" />
              </AvGroup>
              <AvGroup>
                <Label id="useLabel" for="claim-use">
                  <Translate contentKey="hcpNphiesPortalApp.claim.use">Use</Translate>
                </Label>
                <AvInput
                  id="claim-use"
                  data-cy="use"
                  type="select"
                  className="form-control"
                  name="use"
                  value={(!isNew && claimEntity.use) || 'Claim'}
                >
                  <option value="Claim">{translate('hcpNphiesPortalApp.Use.Claim')}</option>
                  <option value="PreAuthorization">{translate('hcpNphiesPortalApp.Use.PreAuthorization')}</option>
                  <option value="Predetermination">{translate('hcpNphiesPortalApp.Use.Predetermination')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="typeLabel" for="claim-type">
                  <Translate contentKey="hcpNphiesPortalApp.claim.type">Type</Translate>
                </Label>
                <AvInput
                  id="claim-type"
                  data-cy="type"
                  type="select"
                  className="form-control"
                  name="type"
                  value={(!isNew && claimEntity.type) || 'Institutional'}
                >
                  <option value="Institutional">{translate('hcpNphiesPortalApp.ClaimTypeEnum.Institutional')}</option>
                  <option value="Oral">{translate('hcpNphiesPortalApp.ClaimTypeEnum.Oral')}</option>
                  <option value="Pharmacy">{translate('hcpNphiesPortalApp.ClaimTypeEnum.Pharmacy')}</option>
                  <option value="Professional">{translate('hcpNphiesPortalApp.ClaimTypeEnum.Professional')}</option>
                  <option value="Vision">{translate('hcpNphiesPortalApp.ClaimTypeEnum.Vision')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="subTypeLabel" for="claim-subType">
                  <Translate contentKey="hcpNphiesPortalApp.claim.subType">Sub Type</Translate>
                </Label>
                <AvInput
                  id="claim-subType"
                  data-cy="subType"
                  type="select"
                  className="form-control"
                  name="subType"
                  value={(!isNew && claimEntity.subType) || 'Ip'}
                >
                  <option value="Ip">{translate('hcpNphiesPortalApp.ClaimSubTypeEnum.Ip')}</option>
                  <option value="Op">{translate('hcpNphiesPortalApp.ClaimSubTypeEnum.Op')}</option>
                  <option value="Emr">{translate('hcpNphiesPortalApp.ClaimSubTypeEnum.Emr')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="eligibilityOfflineLabel" for="claim-eligibilityOffline">
                  <Translate contentKey="hcpNphiesPortalApp.claim.eligibilityOffline">Eligibility Offline</Translate>
                </Label>
                <AvField id="claim-eligibilityOffline" data-cy="eligibilityOffline" type="text" name="eligibilityOffline" />
              </AvGroup>
              <AvGroup>
                <Label id="eligibilityOfflineDateLabel" for="claim-eligibilityOfflineDate">
                  <Translate contentKey="hcpNphiesPortalApp.claim.eligibilityOfflineDate">Eligibility Offline Date</Translate>
                </Label>
                <AvInput
                  id="claim-eligibilityOfflineDate"
                  data-cy="eligibilityOfflineDate"
                  type="datetime-local"
                  className="form-control"
                  name="eligibilityOfflineDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.claimEntity.eligibilityOfflineDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="authorizationOfflineDateLabel" for="claim-authorizationOfflineDate">
                  <Translate contentKey="hcpNphiesPortalApp.claim.authorizationOfflineDate">Authorization Offline Date</Translate>
                </Label>
                <AvInput
                  id="claim-authorizationOfflineDate"
                  data-cy="authorizationOfflineDate"
                  type="datetime-local"
                  className="form-control"
                  name="authorizationOfflineDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.claimEntity.authorizationOfflineDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="billableStartLabel" for="claim-billableStart">
                  <Translate contentKey="hcpNphiesPortalApp.claim.billableStart">Billable Start</Translate>
                </Label>
                <AvInput
                  id="claim-billableStart"
                  data-cy="billableStart"
                  type="datetime-local"
                  className="form-control"
                  name="billableStart"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.claimEntity.billableStart)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="billableEndLabel" for="claim-billableEnd">
                  <Translate contentKey="hcpNphiesPortalApp.claim.billableEnd">Billable End</Translate>
                </Label>
                <AvInput
                  id="claim-billableEnd"
                  data-cy="billableEnd"
                  type="datetime-local"
                  className="form-control"
                  name="billableEnd"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.claimEntity.billableEnd)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="priorityLabel" for="claim-priority">
                  <Translate contentKey="hcpNphiesPortalApp.claim.priority">Priority</Translate>
                </Label>
                <AvInput
                  id="claim-priority"
                  data-cy="priority"
                  type="select"
                  className="form-control"
                  name="priority"
                  value={(!isNew && claimEntity.priority) || 'Stat'}
                >
                  <option value="Stat">{translate('hcpNphiesPortalApp.PriorityEnum.Stat')}</option>
                  <option value="Normal">{translate('hcpNphiesPortalApp.PriorityEnum.Normal')}</option>
                  <option value="Deferred">{translate('hcpNphiesPortalApp.PriorityEnum.Deferred')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="fundsReserveLabel" for="claim-fundsReserve">
                  <Translate contentKey="hcpNphiesPortalApp.claim.fundsReserve">Funds Reserve</Translate>
                </Label>
                <AvInput
                  id="claim-fundsReserve"
                  data-cy="fundsReserve"
                  type="select"
                  className="form-control"
                  name="fundsReserve"
                  value={(!isNew && claimEntity.fundsReserve) || 'Patient'}
                >
                  <option value="Patient">{translate('hcpNphiesPortalApp.FundsReserveEnum.Patient')}</option>
                  <option value="Provider">{translate('hcpNphiesPortalApp.FundsReserveEnum.Provider')}</option>
                  <option value="None">{translate('hcpNphiesPortalApp.FundsReserveEnum.None')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="claim-encounter">
                  <Translate contentKey="hcpNphiesPortalApp.claim.encounter">Encounter</Translate>
                </Label>
                <AvInput id="claim-encounter" data-cy="encounter" type="select" className="form-control" name="encounterId">
                  <option value="" key="0" />
                  {encounters
                    ? encounters.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="claim-eligibilityResponse">
                  <Translate contentKey="hcpNphiesPortalApp.claim.eligibilityResponse">Eligibility Response</Translate>
                </Label>
                <AvInput
                  id="claim-eligibilityResponse"
                  data-cy="eligibilityResponse"
                  type="select"
                  className="form-control"
                  name="eligibilityResponseId"
                >
                  <option value="" key="0" />
                  {coverageEligibilityResponses
                    ? coverageEligibilityResponses.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="claim-patient">
                  <Translate contentKey="hcpNphiesPortalApp.claim.patient">Patient</Translate>
                </Label>
                <AvInput id="claim-patient" data-cy="patient" type="select" className="form-control" name="patientId">
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
                <Label for="claim-provider">
                  <Translate contentKey="hcpNphiesPortalApp.claim.provider">Provider</Translate>
                </Label>
                <AvInput id="claim-provider" data-cy="provider" type="select" className="form-control" name="providerId">
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
                <Label for="claim-insurer">
                  <Translate contentKey="hcpNphiesPortalApp.claim.insurer">Insurer</Translate>
                </Label>
                <AvInput id="claim-insurer" data-cy="insurer" type="select" className="form-control" name="insurerId">
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
                <Label for="claim-prescription">
                  <Translate contentKey="hcpNphiesPortalApp.claim.prescription">Prescription</Translate>
                </Label>
                <AvInput id="claim-prescription" data-cy="prescription" type="select" className="form-control" name="prescriptionId">
                  <option value="" key="0" />
                  {referenceIdentifiers
                    ? referenceIdentifiers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="claim-originalPrescription">
                  <Translate contentKey="hcpNphiesPortalApp.claim.originalPrescription">Original Prescription</Translate>
                </Label>
                <AvInput
                  id="claim-originalPrescription"
                  data-cy="originalPrescription"
                  type="select"
                  className="form-control"
                  name="originalPrescriptionId"
                >
                  <option value="" key="0" />
                  {referenceIdentifiers
                    ? referenceIdentifiers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="claim-payee">
                  <Translate contentKey="hcpNphiesPortalApp.claim.payee">Payee</Translate>
                </Label>
                <AvInput id="claim-payee" data-cy="payee" type="select" className="form-control" name="payeeId">
                  <option value="" key="0" />
                  {payees
                    ? payees.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="claim-referral">
                  <Translate contentKey="hcpNphiesPortalApp.claim.referral">Referral</Translate>
                </Label>
                <AvInput id="claim-referral" data-cy="referral" type="select" className="form-control" name="referralId">
                  <option value="" key="0" />
                  {referenceIdentifiers
                    ? referenceIdentifiers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="claim-facility">
                  <Translate contentKey="hcpNphiesPortalApp.claim.facility">Facility</Translate>
                </Label>
                <AvInput id="claim-facility" data-cy="facility" type="select" className="form-control" name="facilityId">
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
              <AvGroup>
                <Label for="claim-accident">
                  <Translate contentKey="hcpNphiesPortalApp.claim.accident">Accident</Translate>
                </Label>
                <AvInput id="claim-accident" data-cy="accident" type="select" className="form-control" name="accidentId">
                  <option value="" key="0" />
                  {accidents
                    ? accidents.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/claim" replace color="info">
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
  encounters: storeState.encounter.entities,
  coverageEligibilityResponses: storeState.coverageEligibilityResponse.entities,
  patients: storeState.patient.entities,
  organizations: storeState.organization.entities,
  referenceIdentifiers: storeState.referenceIdentifier.entities,
  payees: storeState.payee.entities,
  locations: storeState.location.entities,
  accidents: storeState.accident.entities,
  claimEntity: storeState.claim.entity,
  loading: storeState.claim.loading,
  updating: storeState.claim.updating,
  updateSuccess: storeState.claim.updateSuccess,
});

const mapDispatchToProps = {
  getEncounters,
  getCoverageEligibilityResponses,
  getPatients,
  getOrganizations,
  getReferenceIdentifiers,
  getPayees,
  getLocations,
  getAccidents,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClaimUpdate);
