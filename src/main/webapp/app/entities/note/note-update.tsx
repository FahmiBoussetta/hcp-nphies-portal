import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICommunication } from 'app/shared/model/communication.model';
import { getEntities as getCommunications } from 'app/entities/communication/communication.reducer';
import { ICommunicationRequest } from 'app/shared/model/communication-request.model';
import { getEntities as getCommunicationRequests } from 'app/entities/communication-request/communication-request.reducer';
import { getEntity, updateEntity, createEntity, reset } from './note.reducer';
import { INote } from 'app/shared/model/note.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface INoteUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const NoteUpdate = (props: INoteUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { noteEntity, communications, communicationRequests, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/note');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCommunications();
    props.getCommunicationRequests();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.time = convertDateTimeToServer(values.time);

    if (errors.length === 0) {
      const entity = {
        ...noteEntity,
        ...values,
        communication: communications.find(it => it.id.toString() === values.communicationId.toString()),
        communicationRequest: communicationRequests.find(it => it.id.toString() === values.communicationRequestId.toString()),
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
          <h2 id="hcpNphiesPortalApp.note.home.createOrEditLabel" data-cy="NoteCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.note.home.createOrEditLabel">Create or edit a Note</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : noteEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="note-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="note-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="textLabel" for="note-text">
                  <Translate contentKey="hcpNphiesPortalApp.note.text">Text</Translate>
                </Label>
                <AvField id="note-text" data-cy="text" type="text" name="text" />
              </AvGroup>
              <AvGroup>
                <Label id="authorLabel" for="note-author">
                  <Translate contentKey="hcpNphiesPortalApp.note.author">Author</Translate>
                </Label>
                <AvField id="note-author" data-cy="author" type="text" name="author" />
              </AvGroup>
              <AvGroup>
                <Label id="timeLabel" for="note-time">
                  <Translate contentKey="hcpNphiesPortalApp.note.time">Time</Translate>
                </Label>
                <AvInput
                  id="note-time"
                  data-cy="time"
                  type="datetime-local"
                  className="form-control"
                  name="time"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.noteEntity.time)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="note-communication">
                  <Translate contentKey="hcpNphiesPortalApp.note.communication">Communication</Translate>
                </Label>
                <AvInput id="note-communication" data-cy="communication" type="select" className="form-control" name="communicationId">
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
              <AvGroup>
                <Label for="note-communicationRequest">
                  <Translate contentKey="hcpNphiesPortalApp.note.communicationRequest">Communication Request</Translate>
                </Label>
                <AvInput
                  id="note-communicationRequest"
                  data-cy="communicationRequest"
                  type="select"
                  className="form-control"
                  name="communicationRequestId"
                >
                  <option value="" key="0" />
                  {communicationRequests
                    ? communicationRequests.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/note" replace color="info">
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
  communications: storeState.communication.entities,
  communicationRequests: storeState.communicationRequest.entities,
  noteEntity: storeState.note.entity,
  loading: storeState.note.loading,
  updating: storeState.note.updating,
  updateSuccess: storeState.note.updateSuccess,
});

const mapDispatchToProps = {
  getCommunications,
  getCommunicationRequests,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(NoteUpdate);
