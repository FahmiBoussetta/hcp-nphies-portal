import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IOperationOutcome } from 'app/shared/model/operation-outcome.model';
import { getEntities as getOperationOutcomes } from 'app/entities/operation-outcome/operation-outcome.reducer';
import { getEntity, updateEntity, createEntity, reset } from './ope-out-error-messages.reducer';
import { IOpeOutErrorMessages } from 'app/shared/model/ope-out-error-messages.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOpeOutErrorMessagesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OpeOutErrorMessagesUpdate = (props: IOpeOutErrorMessagesUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { opeOutErrorMessagesEntity, operationOutcomes, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/ope-out-error-messages');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getOperationOutcomes();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...opeOutErrorMessagesEntity,
        ...values,
        operationOutcome: operationOutcomes.find(it => it.id.toString() === values.operationOutcomeId.toString()),
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
          <h2 id="hcpNphiesPortalApp.opeOutErrorMessages.home.createOrEditLabel" data-cy="OpeOutErrorMessagesCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.opeOutErrorMessages.home.createOrEditLabel">
              Create or edit a OpeOutErrorMessages
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : opeOutErrorMessagesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="ope-out-error-messages-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="ope-out-error-messages-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="messageLabel" for="ope-out-error-messages-message">
                  <Translate contentKey="hcpNphiesPortalApp.opeOutErrorMessages.message">Message</Translate>
                </Label>
                <AvField id="ope-out-error-messages-message" data-cy="message" type="text" name="message" />
              </AvGroup>
              <AvGroup>
                <Label for="ope-out-error-messages-operationOutcome">
                  <Translate contentKey="hcpNphiesPortalApp.opeOutErrorMessages.operationOutcome">Operation Outcome</Translate>
                </Label>
                <AvInput
                  id="ope-out-error-messages-operationOutcome"
                  data-cy="operationOutcome"
                  type="select"
                  className="form-control"
                  name="operationOutcomeId"
                >
                  <option value="" key="0" />
                  {operationOutcomes
                    ? operationOutcomes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/ope-out-error-messages" replace color="info">
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
  operationOutcomes: storeState.operationOutcome.entities,
  opeOutErrorMessagesEntity: storeState.opeOutErrorMessages.entity,
  loading: storeState.opeOutErrorMessages.loading,
  updating: storeState.opeOutErrorMessages.updating,
  updateSuccess: storeState.opeOutErrorMessages.updateSuccess,
});

const mapDispatchToProps = {
  getOperationOutcomes,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OpeOutErrorMessagesUpdate);
