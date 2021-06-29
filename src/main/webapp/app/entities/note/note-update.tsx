import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICommunication } from 'app/shared/model/communication.model';
import { getEntities as getCommunications } from 'app/entities/communication/communication.reducer';
import { ICommunicationRequest } from 'app/shared/model/communication-request.model';
import { getEntities as getCommunicationRequests } from 'app/entities/communication-request/communication-request.reducer';
import { getEntity, updateEntity, createEntity, reset } from './note.reducer';
import { INote } from 'app/shared/model/note.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const NoteUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const communications = useAppSelector(state => state.communication.entities);
  const communicationRequests = useAppSelector(state => state.communicationRequest.entities);
  const noteEntity = useAppSelector(state => state.note.entity);
  const loading = useAppSelector(state => state.note.loading);
  const updating = useAppSelector(state => state.note.updating);
  const updateSuccess = useAppSelector(state => state.note.updateSuccess);

  const handleClose = () => {
    props.history.push('/note');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCommunications({}));
    dispatch(getCommunicationRequests({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.time = convertDateTimeToServer(values.time);

    const entity = {
      ...noteEntity,
      ...values,
      communication: communications.find(it => it.id.toString() === values.communicationId.toString()),
      communicationRequest: communicationRequests.find(it => it.id.toString() === values.communicationRequestId.toString()),
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
          time: displayDefaultDateTime(),
        }
      : {
          ...noteEntity,
          time: convertDateTimeFromServer(noteEntity.time),
          communicationId: noteEntity?.communication?.id,
          communicationRequestId: noteEntity?.communicationRequest?.id,
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
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="note-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('hcpNphiesPortalApp.note.text')} id="note-text" name="text" data-cy="text" type="text" />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.note.author')}
                id="note-author"
                name="author"
                data-cy="author"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.note.time')}
                id="note-time"
                name="time"
                data-cy="time"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="note-communication"
                name="communicationId"
                data-cy="communication"
                label={translate('hcpNphiesPortalApp.note.communication')}
                type="select"
              >
                <option value="" key="0" />
                {communications
                  ? communications.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="note-communicationRequest"
                name="communicationRequestId"
                data-cy="communicationRequest"
                label={translate('hcpNphiesPortalApp.note.communicationRequest')}
                type="select"
              >
                <option value="" key="0" />
                {communicationRequests
                  ? communicationRequests.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/note" replace color="info">
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

export default NoteUpdate;
