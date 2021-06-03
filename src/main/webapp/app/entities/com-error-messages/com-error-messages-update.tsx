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
import { getEntity, updateEntity, createEntity, reset } from './com-error-messages.reducer';
import { IComErrorMessages } from 'app/shared/model/com-error-messages.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IComErrorMessagesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ComErrorMessagesUpdate = (props: IComErrorMessagesUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { comErrorMessagesEntity, communications, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/com-error-messages');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCommunications();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...comErrorMessagesEntity,
        ...values,
        communication: communications.find(it => it.id.toString() === values.communicationId.toString()),
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
            <AvForm model={isNew ? {} : comErrorMessagesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="com-error-messages-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="com-error-messages-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="messageLabel" for="com-error-messages-message">
                  <Translate contentKey="hcpNphiesPortalApp.comErrorMessages.message">Message</Translate>
                </Label>
                <AvField id="com-error-messages-message" data-cy="message" type="text" name="message" />
              </AvGroup>
              <AvGroup>
                <Label for="com-error-messages-communication">
                  <Translate contentKey="hcpNphiesPortalApp.comErrorMessages.communication">Communication</Translate>
                </Label>
                <AvInput
                  id="com-error-messages-communication"
                  data-cy="communication"
                  type="select"
                  className="form-control"
                  name="communicationId"
                >
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
              <Button tag={Link} id="cancel-save" to="/com-error-messages" replace color="info">
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
  comErrorMessagesEntity: storeState.comErrorMessages.entity,
  loading: storeState.comErrorMessages.loading,
  updating: storeState.comErrorMessages.updating,
  updateSuccess: storeState.comErrorMessages.updateSuccess,
});

const mapDispatchToProps = {
  getCommunications,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ComErrorMessagesUpdate);
