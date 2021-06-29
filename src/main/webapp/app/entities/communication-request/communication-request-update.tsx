import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

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
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CommunicationRequestUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const patients = useAppSelector(state => state.patient.entities);
  const claims = useAppSelector(state => state.claim.entities);
  const organizations = useAppSelector(state => state.organization.entities);
  const communications = useAppSelector(state => state.communication.entities);
  const communicationRequestEntity = useAppSelector(state => state.communicationRequest.entity);
  const loading = useAppSelector(state => state.communicationRequest.loading);
  const updating = useAppSelector(state => state.communicationRequest.updating);
  const updateSuccess = useAppSelector(state => state.communicationRequest.updateSuccess);

  const handleClose = () => {
    props.history.push('/communication-request');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPatients({}));
    dispatch(getClaims({}));
    dispatch(getOrganizations({}));
    dispatch(getCommunications({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.limitDate = convertDateTimeToServer(values.limitDate);

    const entity = {
      ...communicationRequestEntity,
      ...values,
      subject: patients.find(it => it.id.toString() === values.subjectId.toString()),
      about: claims.find(it => it.id.toString() === values.aboutId.toString()),
      sender: organizations.find(it => it.id.toString() === values.senderId.toString()),
      communication: communications.find(it => it.id.toString() === values.communicationId.toString()),
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
          limitDate: displayDefaultDateTime(),
        }
      : {
          ...communicationRequestEntity,
          limitDate: convertDateTimeFromServer(communicationRequestEntity.limitDate),
          subjectId: communicationRequestEntity?.subject?.id,
          aboutId: communicationRequestEntity?.about?.id,
          senderId: communicationRequestEntity?.sender?.id,
          communicationId: communicationRequestEntity?.communication?.id,
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
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="communication-request-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.communicationRequest.value')}
                id="communication-request-value"
                name="value"
                data-cy="value"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.communicationRequest.system')}
                id="communication-request-system"
                name="system"
                data-cy="system"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.communicationRequest.parsed')}
                id="communication-request-parsed"
                name="parsed"
                data-cy="parsed"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.communicationRequest.limitDate')}
                id="communication-request-limitDate"
                name="limitDate"
                data-cy="limitDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="communication-request-subject"
                name="subjectId"
                data-cy="subject"
                label={translate('hcpNphiesPortalApp.communicationRequest.subject')}
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
                id="communication-request-about"
                name="aboutId"
                data-cy="about"
                label={translate('hcpNphiesPortalApp.communicationRequest.about')}
                type="select"
              >
                <option value="" key="0" />
                {claims
                  ? claims.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="communication-request-sender"
                name="senderId"
                data-cy="sender"
                label={translate('hcpNphiesPortalApp.communicationRequest.sender')}
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
                id="communication-request-communication"
                name="communicationId"
                data-cy="communication"
                label={translate('hcpNphiesPortalApp.communicationRequest.communication')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/communication-request" replace color="info">
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

export default CommunicationRequestUpdate;
