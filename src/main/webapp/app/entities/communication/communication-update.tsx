import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

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
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CommunicationUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const patients = useAppSelector(state => state.patient.entities);
  const organizations = useAppSelector(state => state.organization.entities);
  const claims = useAppSelector(state => state.claim.entities);
  const communicationEntity = useAppSelector(state => state.communication.entity);
  const loading = useAppSelector(state => state.communication.loading);
  const updating = useAppSelector(state => state.communication.updating);
  const updateSuccess = useAppSelector(state => state.communication.updateSuccess);

  const handleClose = () => {
    props.history.push('/communication');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPatients({}));
    dispatch(getOrganizations({}));
    dispatch(getClaims({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...communicationEntity,
      ...values,
      subject: patients.find(it => it.id.toString() === values.subjectId.toString()),
      sender: organizations.find(it => it.id.toString() === values.senderId.toString()),
      recipient: organizations.find(it => it.id.toString() === values.recipientId.toString()),
      about: claims.find(it => it.id.toString() === values.aboutId.toString()),
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
          ...communicationEntity,
          priority: 'Stat',
          subjectId: communicationEntity?.subject?.id,
          senderId: communicationEntity?.sender?.id,
          recipientId: communicationEntity?.recipient?.id,
          aboutId: communicationEntity?.about?.id,
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
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="communication-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.communication.guid')}
                id="communication-guid"
                name="guid"
                data-cy="guid"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.communication.isQueued')}
                id="communication-isQueued"
                name="isQueued"
                data-cy="isQueued"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.communication.parsed')}
                id="communication-parsed"
                name="parsed"
                data-cy="parsed"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.communication.identifier')}
                id="communication-identifier"
                name="identifier"
                data-cy="identifier"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.communication.priority')}
                id="communication-priority"
                name="priority"
                data-cy="priority"
                type="select"
              >
                <option value="Stat">{translate('hcpNphiesPortalApp.CommunicationPriorityEnum.Stat')}</option>
                <option value="Asap">{translate('hcpNphiesPortalApp.CommunicationPriorityEnum.Asap')}</option>
                <option value="Urgent">{translate('hcpNphiesPortalApp.CommunicationPriorityEnum.Urgent')}</option>
                <option value="Routine">{translate('hcpNphiesPortalApp.CommunicationPriorityEnum.Routine')}</option>
              </ValidatedField>
              <ValidatedField
                id="communication-subject"
                name="subjectId"
                data-cy="subject"
                label={translate('hcpNphiesPortalApp.communication.subject')}
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
                id="communication-sender"
                name="senderId"
                data-cy="sender"
                label={translate('hcpNphiesPortalApp.communication.sender')}
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
                id="communication-recipient"
                name="recipientId"
                data-cy="recipient"
                label={translate('hcpNphiesPortalApp.communication.recipient')}
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
                id="communication-about"
                name="aboutId"
                data-cy="about"
                label={translate('hcpNphiesPortalApp.communication.about')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/communication" replace color="info">
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

export default CommunicationUpdate;
