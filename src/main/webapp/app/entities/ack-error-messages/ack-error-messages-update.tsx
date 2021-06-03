import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAcknowledgement } from 'app/shared/model/acknowledgement.model';
import { getEntities as getAcknowledgements } from 'app/entities/acknowledgement/acknowledgement.reducer';
import { getEntity, updateEntity, createEntity, reset } from './ack-error-messages.reducer';
import { IAckErrorMessages } from 'app/shared/model/ack-error-messages.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAckErrorMessagesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AckErrorMessagesUpdate = (props: IAckErrorMessagesUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { ackErrorMessagesEntity, acknowledgements, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/ack-error-messages');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getAcknowledgements();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...ackErrorMessagesEntity,
        ...values,
        acknowledgement: acknowledgements.find(it => it.id.toString() === values.acknowledgementId.toString()),
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
            <AvForm model={isNew ? {} : ackErrorMessagesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="ack-error-messages-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="ack-error-messages-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="messageLabel" for="ack-error-messages-message">
                  <Translate contentKey="hcpNphiesPortalApp.ackErrorMessages.message">Message</Translate>
                </Label>
                <AvField id="ack-error-messages-message" data-cy="message" type="text" name="message" />
              </AvGroup>
              <AvGroup>
                <Label for="ack-error-messages-acknowledgement">
                  <Translate contentKey="hcpNphiesPortalApp.ackErrorMessages.acknowledgement">Acknowledgement</Translate>
                </Label>
                <AvInput
                  id="ack-error-messages-acknowledgement"
                  data-cy="acknowledgement"
                  type="select"
                  className="form-control"
                  name="acknowledgementId"
                >
                  <option value="" key="0" />
                  {acknowledgements
                    ? acknowledgements.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/ack-error-messages" replace color="info">
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
  acknowledgements: storeState.acknowledgement.entities,
  ackErrorMessagesEntity: storeState.ackErrorMessages.entity,
  loading: storeState.ackErrorMessages.loading,
  updating: storeState.ackErrorMessages.updating,
  updateSuccess: storeState.ackErrorMessages.updateSuccess,
});

const mapDispatchToProps = {
  getAcknowledgements,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AckErrorMessagesUpdate);
