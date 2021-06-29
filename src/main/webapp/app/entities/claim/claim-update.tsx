import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

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
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ClaimUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const encounters = useAppSelector(state => state.encounter.entities);
  const coverageEligibilityResponses = useAppSelector(state => state.coverageEligibilityResponse.entities);
  const patients = useAppSelector(state => state.patient.entities);
  const organizations = useAppSelector(state => state.organization.entities);
  const referenceIdentifiers = useAppSelector(state => state.referenceIdentifier.entities);
  const payees = useAppSelector(state => state.payee.entities);
  const locations = useAppSelector(state => state.location.entities);
  const accidents = useAppSelector(state => state.accident.entities);
  const claimEntity = useAppSelector(state => state.claim.entity);
  const loading = useAppSelector(state => state.claim.loading);
  const updating = useAppSelector(state => state.claim.updating);
  const updateSuccess = useAppSelector(state => state.claim.updateSuccess);

  const handleClose = () => {
    props.history.push('/claim');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getEncounters({}));
    dispatch(getCoverageEligibilityResponses({}));
    dispatch(getPatients({}));
    dispatch(getOrganizations({}));
    dispatch(getReferenceIdentifiers({}));
    dispatch(getPayees({}));
    dispatch(getLocations({}));
    dispatch(getAccidents({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.eligibilityOfflineDate = convertDateTimeToServer(values.eligibilityOfflineDate);
    values.authorizationOfflineDate = convertDateTimeToServer(values.authorizationOfflineDate);
    values.billableStart = convertDateTimeToServer(values.billableStart);
    values.billableEnd = convertDateTimeToServer(values.billableEnd);

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
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          eligibilityOfflineDate: displayDefaultDateTime(),
          authorizationOfflineDate: displayDefaultDateTime(),
          billableStart: displayDefaultDateTime(),
          billableEnd: displayDefaultDateTime(),
        }
      : {
          ...claimEntity,
          use: 'Claim',
          type: 'Institutional',
          subType: 'Ip',
          eligibilityOfflineDate: convertDateTimeFromServer(claimEntity.eligibilityOfflineDate),
          authorizationOfflineDate: convertDateTimeFromServer(claimEntity.authorizationOfflineDate),
          billableStart: convertDateTimeFromServer(claimEntity.billableStart),
          billableEnd: convertDateTimeFromServer(claimEntity.billableEnd),
          priority: 'Stat',
          fundsReserve: 'Patient',
          encounterId: claimEntity?.encounter?.id,
          eligibilityResponseId: claimEntity?.eligibilityResponse?.id,
          patientId: claimEntity?.patient?.id,
          providerId: claimEntity?.provider?.id,
          insurerId: claimEntity?.insurer?.id,
          prescriptionId: claimEntity?.prescription?.id,
          originalPrescriptionId: claimEntity?.originalPrescription?.id,
          payeeId: claimEntity?.payee?.id,
          referralId: claimEntity?.referral?.id,
          facilityId: claimEntity?.facility?.id,
          accidentId: claimEntity?.accident?.id,
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
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="claim-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('hcpNphiesPortalApp.claim.guid')} id="claim-guid" name="guid" data-cy="guid" type="text" />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.claim.isQueued')}
                id="claim-isQueued"
                name="isQueued"
                data-cy="isQueued"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.claim.parsed')}
                id="claim-parsed"
                name="parsed"
                data-cy="parsed"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.claim.identifier')}
                id="claim-identifier"
                name="identifier"
                data-cy="identifier"
                type="text"
              />
              <ValidatedField label={translate('hcpNphiesPortalApp.claim.use')} id="claim-use" name="use" data-cy="use" type="select">
                <option value="Claim">{translate('hcpNphiesPortalApp.Use.Claim')}</option>
                <option value="PreAuthorization">{translate('hcpNphiesPortalApp.Use.PreAuthorization')}</option>
                <option value="Predetermination">{translate('hcpNphiesPortalApp.Use.Predetermination')}</option>
              </ValidatedField>
              <ValidatedField label={translate('hcpNphiesPortalApp.claim.type')} id="claim-type" name="type" data-cy="type" type="select">
                <option value="Institutional">{translate('hcpNphiesPortalApp.ClaimTypeEnum.Institutional')}</option>
                <option value="Oral">{translate('hcpNphiesPortalApp.ClaimTypeEnum.Oral')}</option>
                <option value="Pharmacy">{translate('hcpNphiesPortalApp.ClaimTypeEnum.Pharmacy')}</option>
                <option value="Professional">{translate('hcpNphiesPortalApp.ClaimTypeEnum.Professional')}</option>
                <option value="Vision">{translate('hcpNphiesPortalApp.ClaimTypeEnum.Vision')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.claim.subType')}
                id="claim-subType"
                name="subType"
                data-cy="subType"
                type="select"
              >
                <option value="Ip">{translate('hcpNphiesPortalApp.ClaimSubTypeEnum.Ip')}</option>
                <option value="Op">{translate('hcpNphiesPortalApp.ClaimSubTypeEnum.Op')}</option>
                <option value="Emr">{translate('hcpNphiesPortalApp.ClaimSubTypeEnum.Emr')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.claim.eligibilityOffline')}
                id="claim-eligibilityOffline"
                name="eligibilityOffline"
                data-cy="eligibilityOffline"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.claim.eligibilityOfflineDate')}
                id="claim-eligibilityOfflineDate"
                name="eligibilityOfflineDate"
                data-cy="eligibilityOfflineDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.claim.authorizationOfflineDate')}
                id="claim-authorizationOfflineDate"
                name="authorizationOfflineDate"
                data-cy="authorizationOfflineDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.claim.billableStart')}
                id="claim-billableStart"
                name="billableStart"
                data-cy="billableStart"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.claim.billableEnd')}
                id="claim-billableEnd"
                name="billableEnd"
                data-cy="billableEnd"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.claim.priority')}
                id="claim-priority"
                name="priority"
                data-cy="priority"
                type="select"
              >
                <option value="Stat">{translate('hcpNphiesPortalApp.PriorityEnum.Stat')}</option>
                <option value="Normal">{translate('hcpNphiesPortalApp.PriorityEnum.Normal')}</option>
                <option value="Deferred">{translate('hcpNphiesPortalApp.PriorityEnum.Deferred')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.claim.fundsReserve')}
                id="claim-fundsReserve"
                name="fundsReserve"
                data-cy="fundsReserve"
                type="select"
              >
                <option value="Patient">{translate('hcpNphiesPortalApp.FundsReserveEnum.Patient')}</option>
                <option value="Provider">{translate('hcpNphiesPortalApp.FundsReserveEnum.Provider')}</option>
                <option value="None">{translate('hcpNphiesPortalApp.FundsReserveEnum.None')}</option>
              </ValidatedField>
              <ValidatedField
                id="claim-encounter"
                name="encounterId"
                data-cy="encounter"
                label={translate('hcpNphiesPortalApp.claim.encounter')}
                type="select"
              >
                <option value="" key="0" />
                {encounters
                  ? encounters.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="claim-eligibilityResponse"
                name="eligibilityResponseId"
                data-cy="eligibilityResponse"
                label={translate('hcpNphiesPortalApp.claim.eligibilityResponse')}
                type="select"
              >
                <option value="" key="0" />
                {coverageEligibilityResponses
                  ? coverageEligibilityResponses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="claim-patient"
                name="patientId"
                data-cy="patient"
                label={translate('hcpNphiesPortalApp.claim.patient')}
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
                id="claim-provider"
                name="providerId"
                data-cy="provider"
                label={translate('hcpNphiesPortalApp.claim.provider')}
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
                id="claim-insurer"
                name="insurerId"
                data-cy="insurer"
                label={translate('hcpNphiesPortalApp.claim.insurer')}
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
                id="claim-prescription"
                name="prescriptionId"
                data-cy="prescription"
                label={translate('hcpNphiesPortalApp.claim.prescription')}
                type="select"
              >
                <option value="" key="0" />
                {referenceIdentifiers
                  ? referenceIdentifiers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="claim-originalPrescription"
                name="originalPrescriptionId"
                data-cy="originalPrescription"
                label={translate('hcpNphiesPortalApp.claim.originalPrescription')}
                type="select"
              >
                <option value="" key="0" />
                {referenceIdentifiers
                  ? referenceIdentifiers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="claim-payee"
                name="payeeId"
                data-cy="payee"
                label={translate('hcpNphiesPortalApp.claim.payee')}
                type="select"
              >
                <option value="" key="0" />
                {payees
                  ? payees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="claim-referral"
                name="referralId"
                data-cy="referral"
                label={translate('hcpNphiesPortalApp.claim.referral')}
                type="select"
              >
                <option value="" key="0" />
                {referenceIdentifiers
                  ? referenceIdentifiers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="claim-facility"
                name="facilityId"
                data-cy="facility"
                label={translate('hcpNphiesPortalApp.claim.facility')}
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
                id="claim-accident"
                name="accidentId"
                data-cy="accident"
                label={translate('hcpNphiesPortalApp.claim.accident')}
                type="select"
              >
                <option value="" key="0" />
                {accidents
                  ? accidents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/claim" replace color="info">
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

export default ClaimUpdate;
