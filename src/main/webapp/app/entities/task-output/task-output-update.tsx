import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IReferenceIdentifier } from 'app/shared/model/reference-identifier.model';
import { getEntities as getReferenceIdentifiers } from 'app/entities/reference-identifier/reference-identifier.reducer';
import { ITaskResponse } from 'app/shared/model/task-response.model';
import { getEntities as getTaskResponses } from 'app/entities/task-response/task-response.reducer';
import { getEntity, updateEntity, createEntity, reset } from './task-output.reducer';
import { ITaskOutput } from 'app/shared/model/task-output.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITaskOutputUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TaskOutputUpdate = (props: ITaskOutputUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { taskOutputEntity, referenceIdentifiers, taskResponses, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/task-output');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getReferenceIdentifiers();
    props.getTaskResponses();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...taskOutputEntity,
        ...values,
        response: referenceIdentifiers.find(it => it.id.toString() === values.responseId.toString()),
        taskResponse: taskResponses.find(it => it.id.toString() === values.taskResponseId.toString()),
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
          <h2 id="hcpNphiesPortalApp.taskOutput.home.createOrEditLabel" data-cy="TaskOutputCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.taskOutput.home.createOrEditLabel">Create or edit a TaskOutput</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : taskOutputEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="task-output-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="task-output-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="statusLabel" for="task-output-status">
                  <Translate contentKey="hcpNphiesPortalApp.taskOutput.status">Status</Translate>
                </Label>
                <AvField id="task-output-status" data-cy="status" type="text" name="status" />
              </AvGroup>
              <AvGroup>
                <Label id="errorOutputLabel" for="task-output-errorOutput">
                  <Translate contentKey="hcpNphiesPortalApp.taskOutput.errorOutput">Error Output</Translate>
                </Label>
                <AvField id="task-output-errorOutput" data-cy="errorOutput" type="text" name="errorOutput" />
              </AvGroup>
              <AvGroup>
                <Label for="task-output-response">
                  <Translate contentKey="hcpNphiesPortalApp.taskOutput.response">Response</Translate>
                </Label>
                <AvInput id="task-output-response" data-cy="response" type="select" className="form-control" name="responseId">
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
                <Label for="task-output-taskResponse">
                  <Translate contentKey="hcpNphiesPortalApp.taskOutput.taskResponse">Task Response</Translate>
                </Label>
                <AvInput id="task-output-taskResponse" data-cy="taskResponse" type="select" className="form-control" name="taskResponseId">
                  <option value="" key="0" />
                  {taskResponses
                    ? taskResponses.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/task-output" replace color="info">
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
  referenceIdentifiers: storeState.referenceIdentifier.entities,
  taskResponses: storeState.taskResponse.entities,
  taskOutputEntity: storeState.taskOutput.entity,
  loading: storeState.taskOutput.loading,
  updating: storeState.taskOutput.updating,
  updateSuccess: storeState.taskOutput.updateSuccess,
});

const mapDispatchToProps = {
  getReferenceIdentifiers,
  getTaskResponses,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TaskOutputUpdate);
