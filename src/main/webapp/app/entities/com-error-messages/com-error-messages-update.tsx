import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICommunication } from 'app/shared/model/communication.model';
import { getEntities as getCommunications } from 'app/entities/communication/communication.reducer';
import { getEntity, updateEntity, createEntity, reset } from './com-error-messages.reducer';
import { IComErrorMessages } from 'app/shared/model/com-error-messages.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ComErrorMessagesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const communications = useAppSelector(state => state.communication.entities);
  const comErrorMessagesEntity = useAppSelector(state => state.comErrorMessages.entity);
  const loading = useAppSelector(state => state.comErrorMessages.loading);
  const updating = useAppSelector(state => state.comErrorMessages.updating);
  const updateSuccess = useAppSelector(state => state.comErrorMessages.updateSuccess);

  const handleClose = () => {
    props.history.push('/com-error-messages');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCommunications({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...comErrorMessagesEntity,
      ...values,
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
      ? {}
      : {
          ...comErrorMessagesEntity,
          communicationId: comErrorMessagesEntity?.communication?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.comErrorMessages.home.createOrEditLabel" data-cy="ComErrorMessagesCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.comErrorMessages.home.createOrEditLabel">Create or edit a ComErrorMessages</Translate>
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
                  id="com-error-messages-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.comErrorMessages.message')}
                id="com-error-messages-message"
                name="message"
                data-cy="message"
                type="text"
              />
              <ValidatedField
                id="com-error-messages-communication"
                name="communicationId"
                data-cy="communication"
                label={translate('hcpNphiesPortalApp.comErrorMessages.communication')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/com-error-messages" replace color="info">
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

export default ComErrorMessagesUpdate;
