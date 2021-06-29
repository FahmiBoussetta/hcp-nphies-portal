import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAttachment } from 'app/shared/model/attachment.model';
import { getEntities as getAttachments } from 'app/entities/attachment/attachment.reducer';
import { IReferenceIdentifier } from 'app/shared/model/reference-identifier.model';
import { getEntities as getReferenceIdentifiers } from 'app/entities/reference-identifier/reference-identifier.reducer';
import { ICommunication } from 'app/shared/model/communication.model';
import { getEntities as getCommunications } from 'app/entities/communication/communication.reducer';
import { ICommunicationRequest } from 'app/shared/model/communication-request.model';
import { getEntities as getCommunicationRequests } from 'app/entities/communication-request/communication-request.reducer';
import { getEntity, updateEntity, createEntity, reset } from './payload.reducer';
import { IPayload } from 'app/shared/model/payload.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PayloadUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const attachments = useAppSelector(state => state.attachment.entities);
  const referenceIdentifiers = useAppSelector(state => state.referenceIdentifier.entities);
  const communications = useAppSelector(state => state.communication.entities);
  const communicationRequests = useAppSelector(state => state.communicationRequest.entities);
  const payloadEntity = useAppSelector(state => state.payload.entity);
  const loading = useAppSelector(state => state.payload.loading);
  const updating = useAppSelector(state => state.payload.updating);
  const updateSuccess = useAppSelector(state => state.payload.updateSuccess);

  const handleClose = () => {
    props.history.push('/payload');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAttachments({}));
    dispatch(getReferenceIdentifiers({}));
    dispatch(getCommunications({}));
    dispatch(getCommunicationRequests({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...payloadEntity,
      ...values,
      contentAttachment: attachments.find(it => it.id.toString() === values.contentAttachmentId.toString()),
      contentReference: referenceIdentifiers.find(it => it.id.toString() === values.contentReferenceId.toString()),
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
      ? {}
      : {
          ...payloadEntity,
          contentAttachmentId: payloadEntity?.contentAttachment?.id,
          contentReferenceId: payloadEntity?.contentReference?.id,
          communicationId: payloadEntity?.communication?.id,
          communicationRequestId: payloadEntity?.communicationRequest?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.payload.home.createOrEditLabel" data-cy="PayloadCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.payload.home.createOrEditLabel">Create or edit a Payload</Translate>
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
                  id="payload-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.payload.contentString')}
                id="payload-contentString"
                name="contentString"
                data-cy="contentString"
                type="text"
              />
              <ValidatedField
                id="payload-contentAttachment"
                name="contentAttachmentId"
                data-cy="contentAttachment"
                label={translate('hcpNphiesPortalApp.payload.contentAttachment')}
                type="select"
              >
                <option value="" key="0" />
                {attachments
                  ? attachments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="payload-contentReference"
                name="contentReferenceId"
                data-cy="contentReference"
                label={translate('hcpNphiesPortalApp.payload.contentReference')}
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
                id="payload-communication"
                name="communicationId"
                data-cy="communication"
                label={translate('hcpNphiesPortalApp.payload.communication')}
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
                id="payload-communicationRequest"
                name="communicationRequestId"
                data-cy="communicationRequest"
                label={translate('hcpNphiesPortalApp.payload.communicationRequest')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/payload" replace color="info">
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

export default PayloadUpdate;
