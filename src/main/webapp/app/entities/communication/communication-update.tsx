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
import { IClaim } from 'app/shared/model/claim.model';
import { getEntities as getClaims } from 'app/entities/claim/claim.reducer';
import { getEntity, updateEntity, createEntity, reset } from './communication.reducer';
import { ICommunication } from 'app/shared/model/communication.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICommunicationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CommunicationUpdate = (props: ICommunicationUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { communicationEntity, patients, organizations, claims, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/communication');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPatients();
    props.getOrganizations();
    props.getClaims();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...communicationEntity,
        ...values,
        subject: patients.find(it => it.id.toString() === values.subjectId.toString()),
        sender: organizations.find(it => it.id.toString() === values.senderId.toString()),
        recipient: organizations.find(it => it.id.toString() === values.recipientId.toString()),
        about: claims.find(it => it.id.toString() === values.aboutId.toString()),
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
          <h2 id="hcpNphiesPortalApp.communication.home.createOrEditLabel" data-cy="CommunicationCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.communication.home.createOrEditLabel">Create or edit a Communication</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : communicationEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="communication-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="communication-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="guidLabel" for="communication-guid">
                  <Translate contentKey="hcpNphiesPortalApp.communication.guid">Guid</Translate>
                </Label>
                <AvField id="communication-guid" data-cy="guid" type="text" name="guid" />
              </AvGroup>
              <AvGroup check>
                <Label id="isQueuedLabel">
                  <AvInput id="communication-isQueued" data-cy="isQueued" type="checkbox" className="form-check-input" name="isQueued" />
                  <Translate contentKey="hcpNphiesPortalApp.communication.isQueued">Is Queued</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="parsedLabel" for="communication-parsed">
                  <Translate contentKey="hcpNphiesPortalApp.communication.parsed">Parsed</Translate>
                </Label>
                <AvField id="communication-parsed" data-cy="parsed" type="text" name="parsed" />
              </AvGroup>
              <AvGroup>
                <Label id="identifierLabel" for="communication-identifier">
                  <Translate contentKey="hcpNphiesPortalApp.communication.identifier">Identifier</Translate>
                </Label>
                <AvField id="communication-identifier" data-cy="identifier" type="text" name="identifier" />
              </AvGroup>
              <AvGroup>
                <Label id="priorityLabel" for="communication-priority">
                  <Translate contentKey="hcpNphiesPortalApp.communication.priority">Priority</Translate>
                </Label>
                <AvInput
                  id="communication-priority"
                  data-cy="priority"
                  type="select"
                  className="form-control"
                  name="priority"
                  value={(!isNew && communicationEntity.priority) || 'Stat'}
                >
                  <option value="Stat">{translate('hcpNphiesPortalApp.CommunicationPriorityEnum.Stat')}</option>
                  <option value="Asap">{translate('hcpNphiesPortalApp.CommunicationPriorityEnum.Asap')}</option>
                  <option value="Urgent">{translate('hcpNphiesPortalApp.CommunicationPriorityEnum.Urgent')}</option>
                  <option value="Routine">{translate('hcpNphiesPortalApp.CommunicationPriorityEnum.Routine')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="communication-subject">
                  <Translate contentKey="hcpNphiesPortalApp.communication.subject">Subject</Translate>
                </Label>
                <AvInput id="communication-subject" data-cy="subject" type="select" className="form-control" name="subjectId">
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
                <Label for="communication-sender">
                  <Translate contentKey="hcpNphiesPortalApp.communication.sender">Sender</Translate>
                </Label>
                <AvInput id="communication-sender" data-cy="sender" type="select" className="form-control" name="senderId">
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
                <Label for="communication-recipient">
                  <Translate contentKey="hcpNphiesPortalApp.communication.recipient">Recipient</Translate>
                </Label>
                <AvInput id="communication-recipient" data-cy="recipient" type="select" className="form-control" name="recipientId">
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
                <Label for="communication-about">
                  <Translate contentKey="hcpNphiesPortalApp.communication.about">About</Translate>
                </Label>
                <AvInput id="communication-about" data-cy="about" type="select" className="form-control" name="aboutId">
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
              <Button tag={Link} id="cancel-save" to="/communication" replace color="info">
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
  claims: storeState.claim.entities,
  communicationEntity: storeState.communication.entity,
  loading: storeState.communication.loading,
  updating: storeState.communication.updating,
  updateSuccess: storeState.communication.updateSuccess,
});

const mapDispatchToProps = {
  getPatients,
  getOrganizations,
  getClaims,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CommunicationUpdate);
