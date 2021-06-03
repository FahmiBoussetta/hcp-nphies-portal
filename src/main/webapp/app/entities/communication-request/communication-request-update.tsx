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
import { IClaim } from 'app/shared/model/claim.model';
import { getEntities as getClaims } from 'app/entities/claim/claim.reducer';
import { IOrganization } from 'app/shared/model/organization.model';
import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { ICommunication } from 'app/shared/model/communication.model';
import { getEntities as getCommunications } from 'app/entities/communication/communication.reducer';
import { getEntity, updateEntity, createEntity, reset } from './communication-request.reducer';
import { ICommunicationRequest } from 'app/shared/model/communication-request.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICommunicationRequestUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CommunicationRequestUpdate = (props: ICommunicationRequestUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { communicationRequestEntity, patients, claims, organizations, communications, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/communication-request');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPatients();
    props.getClaims();
    props.getOrganizations();
    props.getCommunications();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.limitDate = convertDateTimeToServer(values.limitDate);

    if (errors.length === 0) {
      const entity = {
        ...communicationRequestEntity,
        ...values,
        subject: patients.find(it => it.id.toString() === values.subjectId.toString()),
        about: claims.find(it => it.id.toString() === values.aboutId.toString()),
        sender: organizations.find(it => it.id.toString() === values.senderId.toString()),
        communication: communications.find(it => it.id.toString() === values.communicationId.toString()),
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
          <h2 id="hcpNphiesPortalApp.communicationRequest.home.createOrEditLabel" data-cy="CommunicationRequestCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.communicationRequest.home.createOrEditLabel">
              Create or edit a CommunicationRequest
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : communicationRequestEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="communication-request-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="communication-request-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="valueLabel" for="communication-request-value">
                  <Translate contentKey="hcpNphiesPortalApp.communicationRequest.value">Value</Translate>
                </Label>
                <AvField id="communication-request-value" data-cy="value" type="text" name="value" />
              </AvGroup>
              <AvGroup>
                <Label id="systemLabel" for="communication-request-system">
                  <Translate contentKey="hcpNphiesPortalApp.communicationRequest.system">System</Translate>
                </Label>
                <AvField id="communication-request-system" data-cy="system" type="text" name="system" />
              </AvGroup>
              <AvGroup>
                <Label id="parsedLabel" for="communication-request-parsed">
                  <Translate contentKey="hcpNphiesPortalApp.communicationRequest.parsed">Parsed</Translate>
                </Label>
                <AvField id="communication-request-parsed" data-cy="parsed" type="text" name="parsed" />
              </AvGroup>
              <AvGroup>
                <Label id="limitDateLabel" for="communication-request-limitDate">
                  <Translate contentKey="hcpNphiesPortalApp.communicationRequest.limitDate">Limit Date</Translate>
                </Label>
                <AvInput
                  id="communication-request-limitDate"
                  data-cy="limitDate"
                  type="datetime-local"
                  className="form-control"
                  name="limitDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.communicationRequestEntity.limitDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="communication-request-subject">
                  <Translate contentKey="hcpNphiesPortalApp.communicationRequest.subject">Subject</Translate>
                </Label>
                <AvInput id="communication-request-subject" data-cy="subject" type="select" className="form-control" name="subjectId">
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
                <Label for="communication-request-about">
                  <Translate contentKey="hcpNphiesPortalApp.communicationRequest.about">About</Translate>
                </Label>
                <AvInput id="communication-request-about" data-cy="about" type="select" className="form-control" name="aboutId">
                  <option value="" key="0" />
                  {claims
                    ? claims.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="communication-request-sender">
                  <Translate contentKey="hcpNphiesPortalApp.communicationRequest.sender">Sender</Translate>
                </Label>
                <AvInput id="communication-request-sender" data-cy="sender" type="select" className="form-control" name="senderId">
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
                <Label for="communication-request-communication">
                  <Translate contentKey="hcpNphiesPortalApp.communicationRequest.communication">Communication</Translate>
                </Label>
                <AvInput
                  id="communication-request-communication"
                  data-cy="communication"
                  type="select"
                  className="form-control"
                  name="communicationId"
                >
                  <option value="" key="0" />
                  {communications
                    ? communications.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/communication-request" replace color="info">
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
  claims: storeState.claim.entities,
  organizations: storeState.organization.entities,
  communications: storeState.communication.entities,
  communicationRequestEntity: storeState.communicationRequest.entity,
  loading: storeState.communicationRequest.loading,
  updating: storeState.communicationRequest.updating,
  updateSuccess: storeState.communicationRequest.updateSuccess,
});

const mapDispatchToProps = {
  getPatients,
  getClaims,
  getOrganizations,
  getCommunications,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CommunicationRequestUpdate);
