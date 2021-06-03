import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

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

export interface IPayloadUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PayloadUpdate = (props: IPayloadUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { payloadEntity, attachments, referenceIdentifiers, communications, communicationRequests, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/payload');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getAttachments();
    props.getReferenceIdentifiers();
    props.getCommunications();
    props.getCommunicationRequests();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...payloadEntity,
        ...values,
        contentAttachment: attachments.find(it => it.id.toString() === values.contentAttachmentId.toString()),
        contentReference: referenceIdentifiers.find(it => it.id.toString() === values.contentReferenceId.toString()),
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
            <AvForm model={isNew ? {} : payloadEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="payload-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="payload-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="contentStringLabel" for="payload-contentString">
                  <Translate contentKey="hcpNphiesPortalApp.payload.contentString">Content String</Translate>
                </Label>
                <AvField id="payload-contentString" data-cy="contentString" type="text" name="contentString" />
              </AvGroup>
              <AvGroup>
                <Label for="payload-contentAttachment">
                  <Translate contentKey="hcpNphiesPortalApp.payload.contentAttachment">Content Attachment</Translate>
                </Label>
                <AvInput
                  id="payload-contentAttachment"
                  data-cy="contentAttachment"
                  type="select"
                  className="form-control"
                  name="contentAttachmentId"
                >
                  <option value="" key="0" />
                  {attachments
                    ? attachments.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="payload-contentReference">
                  <Translate contentKey="hcpNphiesPortalApp.payload.contentReference">Content Reference</Translate>
                </Label>
                <AvInput
                  id="payload-contentReference"
                  data-cy="contentReference"
                  type="select"
                  className="form-control"
                  name="contentReferenceId"
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
                <Label for="payload-communication">
                  <Translate contentKey="hcpNphiesPortalApp.payload.communication">Communication</Translate>
                </Label>
                <AvInput id="payload-communication" data-cy="communication" type="select" className="form-control" name="communicationId">
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
                <Label for="payload-communicationRequest">
                  <Translate contentKey="hcpNphiesPortalApp.payload.communicationRequest">Communication Request</Translate>
                </Label>
                <AvInput
                  id="payload-communicationRequest"
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
              <Button tag={Link} id="cancel-save" to="/payload" replace color="info">
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
  attachments: storeState.attachment.entities,
  referenceIdentifiers: storeState.referenceIdentifier.entities,
  communications: storeState.communication.entities,
  communicationRequests: storeState.communicationRequest.entities,
  payloadEntity: storeState.payload.entity,
  loading: storeState.payload.loading,
  updating: storeState.payload.updating,
  updateSuccess: storeState.payload.updateSuccess,
});

const mapDispatchToProps = {
  getAttachments,
  getReferenceIdentifiers,
  getCommunications,
  getCommunicationRequests,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PayloadUpdate);
