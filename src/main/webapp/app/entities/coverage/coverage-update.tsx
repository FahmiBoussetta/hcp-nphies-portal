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
import { ICoverageEligibilityRequest } from 'app/shared/model/coverage-eligibility-request.model';
import { getEntities as getCoverageEligibilityRequests } from 'app/entities/coverage-eligibility-request/coverage-eligibility-request.reducer';
import { getEntity, updateEntity, createEntity, reset } from './coverage.reducer';
import { ICoverage } from 'app/shared/model/coverage.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICoverageUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CoverageUpdate = (props: ICoverageUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { coverageEntity, patients, organizations, coverageEligibilityRequests, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/coverage');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPatients();
    props.getOrganizations();
    props.getCoverageEligibilityRequests();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...coverageEntity,
        ...values,
        subscriberPatient: patients.find(it => it.id.toString() === values.subscriberPatientId.toString()),
        beneficiary: patients.find(it => it.id.toString() === values.beneficiaryId.toString()),
        payor: organizations.find(it => it.id.toString() === values.payorId.toString()),
        coverageEligibilityRequest: coverageEligibilityRequests.find(
          it => it.id.toString() === values.coverageEligibilityRequestId.toString()
        ),
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
            <AvForm model={isNew ? {} : coverageEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="coverage-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="coverage-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="guidLabel" for="coverage-guid">
                  <Translate contentKey="hcpNphiesPortalApp.coverage.guid">Guid</Translate>
                </Label>
                <AvField id="coverage-guid" data-cy="guid" type="text" name="guid" />
              </AvGroup>
              <AvGroup>
                <Label id="forceIdLabel" for="coverage-forceId">
                  <Translate contentKey="hcpNphiesPortalApp.coverage.forceId">Force Id</Translate>
                </Label>
                <AvField id="coverage-forceId" data-cy="forceId" type="text" name="forceId" />
              </AvGroup>
              <AvGroup>
                <Label id="coverageTypeLabel" for="coverage-coverageType">
                  <Translate contentKey="hcpNphiesPortalApp.coverage.coverageType">Coverage Type</Translate>
                </Label>
                <AvInput
                  id="coverage-coverageType"
                  data-cy="coverageType"
                  type="select"
                  className="form-control"
                  name="coverageType"
                  value={(!isNew && coverageEntity.coverageType) || 'EHCPOL'}
                >
                  <option value="EHCPOL">{translate('hcpNphiesPortalApp.CoverageTypeEnum.EHCPOL')}</option>
                  <option value="PUBLICPOL">{translate('hcpNphiesPortalApp.CoverageTypeEnum.PUBLICPOL')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="subscriberIdLabel" for="coverage-subscriberId">
                  <Translate contentKey="hcpNphiesPortalApp.coverage.subscriberId">Subscriber Id</Translate>
                </Label>
                <AvField id="coverage-subscriberId" data-cy="subscriberId" type="text" name="subscriberId" />
              </AvGroup>
              <AvGroup>
                <Label id="dependentLabel" for="coverage-dependent">
                  <Translate contentKey="hcpNphiesPortalApp.coverage.dependent">Dependent</Translate>
                </Label>
                <AvField id="coverage-dependent" data-cy="dependent" type="text" name="dependent" />
              </AvGroup>
              <AvGroup>
                <Label id="relationShipLabel" for="coverage-relationShip">
                  <Translate contentKey="hcpNphiesPortalApp.coverage.relationShip">Relation Ship</Translate>
                </Label>
                <AvInput
                  id="coverage-relationShip"
                  data-cy="relationShip"
                  type="select"
                  className="form-control"
                  name="relationShip"
                  value={(!isNew && coverageEntity.relationShip) || 'Child'}
                >
                  <option value="Child">{translate('hcpNphiesPortalApp.RelationShipEnum.Child')}</option>
                  <option value="Parent">{translate('hcpNphiesPortalApp.RelationShipEnum.Parent')}</option>
                  <option value="Spouse">{translate('hcpNphiesPortalApp.RelationShipEnum.Spouse')}</option>
                  <option value="Common">{translate('hcpNphiesPortalApp.RelationShipEnum.Common')}</option>
                  <option value="Other">{translate('hcpNphiesPortalApp.RelationShipEnum.Other')}</option>
                  <option value="Self">{translate('hcpNphiesPortalApp.RelationShipEnum.Self')}</option>
                  <option value="Injured">{translate('hcpNphiesPortalApp.RelationShipEnum.Injured')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="networkLabel" for="coverage-network">
                  <Translate contentKey="hcpNphiesPortalApp.coverage.network">Network</Translate>
                </Label>
                <AvField id="coverage-network" data-cy="network" type="text" name="network" />
              </AvGroup>
              <AvGroup check>
                <Label id="subrogationLabel">
                  <AvInput
                    id="coverage-subrogation"
                    data-cy="subrogation"
                    type="checkbox"
                    className="form-check-input"
                    name="subrogation"
                  />
                  <Translate contentKey="hcpNphiesPortalApp.coverage.subrogation">Subrogation</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="coverage-subscriberPatient">
                  <Translate contentKey="hcpNphiesPortalApp.coverage.subscriberPatient">Subscriber Patient</Translate>
                </Label>
                <AvInput
                  id="coverage-subscriberPatient"
                  data-cy="subscriberPatient"
                  type="select"
                  className="form-control"
                  name="subscriberPatientId"
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
                <Label for="coverage-beneficiary">
                  <Translate contentKey="hcpNphiesPortalApp.coverage.beneficiary">Beneficiary</Translate>
                </Label>
                <AvInput id="coverage-beneficiary" data-cy="beneficiary" type="select" className="form-control" name="beneficiaryId">
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
                <Label for="coverage-payor">
                  <Translate contentKey="hcpNphiesPortalApp.coverage.payor">Payor</Translate>
                </Label>
                <AvInput id="coverage-payor" data-cy="payor" type="select" className="form-control" name="payorId">
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
                <Label for="coverage-coverageEligibilityRequest">
                  <Translate contentKey="hcpNphiesPortalApp.coverage.coverageEligibilityRequest">Coverage Eligibility Request</Translate>
                </Label>
                <AvInput
                  id="coverage-coverageEligibilityRequest"
                  data-cy="coverageEligibilityRequest"
                  type="select"
                  className="form-control"
                  name="coverageEligibilityRequestId"
                >
                  <option value="" key="0" />
                  {coverageEligibilityRequests
                    ? coverageEligibilityRequests.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/coverage" replace color="info">
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
  coverageEligibilityRequests: storeState.coverageEligibilityRequest.entities,
  coverageEntity: storeState.coverage.entity,
  loading: storeState.coverage.loading,
  updating: storeState.coverage.updating,
  updateSuccess: storeState.coverage.updateSuccess,
});

const mapDispatchToProps = {
  getPatients,
  getOrganizations,
  getCoverageEligibilityRequests,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CoverageUpdate);
