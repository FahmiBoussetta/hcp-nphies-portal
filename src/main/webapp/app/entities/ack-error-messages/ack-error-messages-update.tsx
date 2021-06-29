import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAcknowledgement } from 'app/shared/model/acknowledgement.model';
import { getEntities as getAcknowledgements } from 'app/entities/acknowledgement/acknowledgement.reducer';
import { getEntity, updateEntity, createEntity, reset } from './ack-error-messages.reducer';
import { IAckErrorMessages } from 'app/shared/model/ack-error-messages.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AckErrorMessagesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const acknowledgements = useAppSelector(state => state.acknowledgement.entities);
  const ackErrorMessagesEntity = useAppSelector(state => state.ackErrorMessages.entity);
  const loading = useAppSelector(state => state.ackErrorMessages.loading);
  const updating = useAppSelector(state => state.ackErrorMessages.updating);
  const updateSuccess = useAppSelector(state => state.ackErrorMessages.updateSuccess);

  const handleClose = () => {
    props.history.push('/ack-error-messages');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAcknowledgements({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...ackErrorMessagesEntity,
      ...values,
      acknowledgement: acknowledgements.find(it => it.id.toString() === values.acknowledgementId.toString()),
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
          ...ackErrorMessagesEntity,
          acknowledgementId: ackErrorMessagesEntity?.acknowledgement?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.ackErrorMessages.home.createOrEditLabel" data-cy="AckErrorMessagesCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.ackErrorMessages.home.createOrEditLabel">Create or edit a AckErrorMessages</Translate>
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
                  id="ack-error-messages-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.ackErrorMessages.message')}
                id="ack-error-messages-message"
                name="message"
                data-cy="message"
                type="text"
              />
              <ValidatedField
                id="ack-error-messages-acknowledgement"
                name="acknowledgementId"
                data-cy="acknowledgement"
                label={translate('hcpNphiesPortalApp.ackErrorMessages.acknowledgement')}
                type="select"
              >
                <option value="" key="0" />
                {acknowledgements
                  ? acknowledgements.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ack-error-messages" replace color="info">
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

export default AckErrorMessagesUpdate;
