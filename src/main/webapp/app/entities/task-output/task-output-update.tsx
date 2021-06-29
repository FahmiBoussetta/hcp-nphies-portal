import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IReferenceIdentifier } from 'app/shared/model/reference-identifier.model';
import { getEntities as getReferenceIdentifiers } from 'app/entities/reference-identifier/reference-identifier.reducer';
import { ITaskResponse } from 'app/shared/model/task-response.model';
import { getEntities as getTaskResponses } from 'app/entities/task-response/task-response.reducer';
import { getEntity, updateEntity, createEntity, reset } from './task-output.reducer';
import { ITaskOutput } from 'app/shared/model/task-output.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TaskOutputUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const referenceIdentifiers = useAppSelector(state => state.referenceIdentifier.entities);
  const taskResponses = useAppSelector(state => state.taskResponse.entities);
  const taskOutputEntity = useAppSelector(state => state.taskOutput.entity);
  const loading = useAppSelector(state => state.taskOutput.loading);
  const updating = useAppSelector(state => state.taskOutput.updating);
  const updateSuccess = useAppSelector(state => state.taskOutput.updateSuccess);

  const handleClose = () => {
    props.history.push('/task-output');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getReferenceIdentifiers({}));
    dispatch(getTaskResponses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...taskOutputEntity,
      ...values,
      response: referenceIdentifiers.find(it => it.id.toString() === values.responseId.toString()),
      taskResponse: taskResponses.find(it => it.id.toString() === values.taskResponseId.toString()),
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
          ...taskOutputEntity,
          responseId: taskOutputEntity?.response?.id,
          taskResponseId: taskOutputEntity?.taskResponse?.id,
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
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="task-output-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.taskOutput.status')}
                id="task-output-status"
                name="status"
                data-cy="status"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.taskOutput.errorOutput')}
                id="task-output-errorOutput"
                name="errorOutput"
                data-cy="errorOutput"
                type="text"
              />
              <ValidatedField
                id="task-output-response"
                name="responseId"
                data-cy="response"
                label={translate('hcpNphiesPortalApp.taskOutput.response')}
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
                id="task-output-taskResponse"
                name="taskResponseId"
                data-cy="taskResponse"
                label={translate('hcpNphiesPortalApp.taskOutput.taskResponse')}
                type="select"
              >
                <option value="" key="0" />
                {taskResponses
                  ? taskResponses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/task-output" replace color="info">
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

export default TaskOutputUpdate;
