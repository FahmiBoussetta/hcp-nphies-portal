import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IReferenceIdentifier } from 'app/shared/model/reference-identifier.model';
import { getEntities as getReferenceIdentifiers } from 'app/entities/reference-identifier/reference-identifier.reducer';
import { ITask } from 'app/shared/model/task.model';
import { getEntities as getTasks } from 'app/entities/task/task.reducer';
import { getEntity, updateEntity, createEntity, reset } from './task-input.reducer';
import { ITaskInput } from 'app/shared/model/task-input.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TaskInputUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const referenceIdentifiers = useAppSelector(state => state.referenceIdentifier.entities);
  const tasks = useAppSelector(state => state.task.entities);
  const taskInputEntity = useAppSelector(state => state.taskInput.entity);
  const loading = useAppSelector(state => state.taskInput.loading);
  const updating = useAppSelector(state => state.taskInput.updating);
  const updateSuccess = useAppSelector(state => state.taskInput.updateSuccess);

  const handleClose = () => {
    props.history.push('/task-input');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getReferenceIdentifiers({}));
    dispatch(getTasks({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.inputStart = convertDateTimeToServer(values.inputStart);
    values.inputEnd = convertDateTimeToServer(values.inputEnd);

    const entity = {
      ...taskInputEntity,
      ...values,
      inputOrigResponse: referenceIdentifiers.find(it => it.id.toString() === values.inputOrigResponseId.toString()),
      task: tasks.find(it => it.id.toString() === values.taskId.toString()),
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
          inputStart: displayDefaultDateTime(),
          inputEnd: displayDefaultDateTime(),
        }
      : {
          ...taskInputEntity,
          inputInclude: 'Claim',
          inputExclude: 'Claim',
          inputIncludeMessage: 'Eligibility_request',
          inputExcludeMessage: 'Eligibility_request',
          inputStart: convertDateTimeFromServer(taskInputEntity.inputStart),
          inputEnd: convertDateTimeFromServer(taskInputEntity.inputEnd),
          inputOrigResponseId: taskInputEntity?.inputOrigResponse?.id,
          taskId: taskInputEntity?.task?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.taskInput.home.createOrEditLabel" data-cy="TaskInputCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.taskInput.home.createOrEditLabel">Create or edit a TaskInput</Translate>
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
                  id="task-input-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.taskInput.inputInclude')}
                id="task-input-inputInclude"
                name="inputInclude"
                data-cy="inputInclude"
                type="select"
              >
                <option value="Claim">{translate('hcpNphiesPortalApp.ResourceTypeEnum.Claim')}</option>
                <option value="ClaimResponse">{translate('hcpNphiesPortalApp.ResourceTypeEnum.ClaimResponse')}</option>
                <option value="Communication">{translate('hcpNphiesPortalApp.ResourceTypeEnum.Communication')}</option>
                <option value="CommunicationRequest">{translate('hcpNphiesPortalApp.ResourceTypeEnum.CommunicationRequest')}</option>
                <option value="CoverageEligibilityRequest">
                  {translate('hcpNphiesPortalApp.ResourceTypeEnum.CoverageEligibilityRequest')}
                </option>
                <option value="CoverageEligibilityResponse">
                  {translate('hcpNphiesPortalApp.ResourceTypeEnum.CoverageEligibilityResponse')}
                </option>
                <option value="OperationOutcome">{translate('hcpNphiesPortalApp.ResourceTypeEnum.OperationOutcome')}</option>
                <option value="PaymentNotice">{translate('hcpNphiesPortalApp.ResourceTypeEnum.PaymentNotice')}</option>
                <option value="PaymentReconciliation">{translate('hcpNphiesPortalApp.ResourceTypeEnum.PaymentReconciliation')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.taskInput.inputExclude')}
                id="task-input-inputExclude"
                name="inputExclude"
                data-cy="inputExclude"
                type="select"
              >
                <option value="Claim">{translate('hcpNphiesPortalApp.ResourceTypeEnum.Claim')}</option>
                <option value="ClaimResponse">{translate('hcpNphiesPortalApp.ResourceTypeEnum.ClaimResponse')}</option>
                <option value="Communication">{translate('hcpNphiesPortalApp.ResourceTypeEnum.Communication')}</option>
                <option value="CommunicationRequest">{translate('hcpNphiesPortalApp.ResourceTypeEnum.CommunicationRequest')}</option>
                <option value="CoverageEligibilityRequest">
                  {translate('hcpNphiesPortalApp.ResourceTypeEnum.CoverageEligibilityRequest')}
                </option>
                <option value="CoverageEligibilityResponse">
                  {translate('hcpNphiesPortalApp.ResourceTypeEnum.CoverageEligibilityResponse')}
                </option>
                <option value="OperationOutcome">{translate('hcpNphiesPortalApp.ResourceTypeEnum.OperationOutcome')}</option>
                <option value="PaymentNotice">{translate('hcpNphiesPortalApp.ResourceTypeEnum.PaymentNotice')}</option>
                <option value="PaymentReconciliation">{translate('hcpNphiesPortalApp.ResourceTypeEnum.PaymentReconciliation')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.taskInput.inputIncludeMessage')}
                id="task-input-inputIncludeMessage"
                name="inputIncludeMessage"
                data-cy="inputIncludeMessage"
                type="select"
              >
                <option value="Eligibility_request">{translate('hcpNphiesPortalApp.EventCodingEnum.Eligibility_request')}</option>
                <option value="Eligibility_response">{translate('hcpNphiesPortalApp.EventCodingEnum.Eligibility_response')}</option>
                <option value="Priorauth_request">{translate('hcpNphiesPortalApp.EventCodingEnum.Priorauth_request')}</option>
                <option value="Priorauth_response">{translate('hcpNphiesPortalApp.EventCodingEnum.Priorauth_response')}</option>
                <option value="Claim_request">{translate('hcpNphiesPortalApp.EventCodingEnum.Claim_request')}</option>
                <option value="Claim_response">{translate('hcpNphiesPortalApp.EventCodingEnum.Claim_response')}</option>
                <option value="Batch_request">{translate('hcpNphiesPortalApp.EventCodingEnum.Batch_request')}</option>
                <option value="Status_check">{translate('hcpNphiesPortalApp.EventCodingEnum.Status_check')}</option>
                <option value="Status_response">{translate('hcpNphiesPortalApp.EventCodingEnum.Status_response')}</option>
                <option value="Cancel_request">{translate('hcpNphiesPortalApp.EventCodingEnum.Cancel_request')}</option>
                <option value="Cancel_response">{translate('hcpNphiesPortalApp.EventCodingEnum.Cancel_response')}</option>
                <option value="Payment_notice">{translate('hcpNphiesPortalApp.EventCodingEnum.Payment_notice')}</option>
                <option value="Payment_reconciliation">{translate('hcpNphiesPortalApp.EventCodingEnum.Payment_reconciliation')}</option>
                <option value="Communication_request">{translate('hcpNphiesPortalApp.EventCodingEnum.Communication_request')}</option>
                <option value="Communication">{translate('hcpNphiesPortalApp.EventCodingEnum.Communication')}</option>
                <option value="Acknowledgement">{translate('hcpNphiesPortalApp.EventCodingEnum.Acknowledgement')}</option>
                <option value="Poll_request">{translate('hcpNphiesPortalApp.EventCodingEnum.Poll_request')}</option>
                <option value="Poll_response">{translate('hcpNphiesPortalApp.EventCodingEnum.Poll_response')}</option>
                <option value="Nullify_request">{translate('hcpNphiesPortalApp.EventCodingEnum.Nullify_request')}</option>
                <option value="Nullify_response">{translate('hcpNphiesPortalApp.EventCodingEnum.Nullify_response')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.taskInput.inputExcludeMessage')}
                id="task-input-inputExcludeMessage"
                name="inputExcludeMessage"
                data-cy="inputExcludeMessage"
                type="select"
              >
                <option value="Eligibility_request">{translate('hcpNphiesPortalApp.EventCodingEnum.Eligibility_request')}</option>
                <option value="Eligibility_response">{translate('hcpNphiesPortalApp.EventCodingEnum.Eligibility_response')}</option>
                <option value="Priorauth_request">{translate('hcpNphiesPortalApp.EventCodingEnum.Priorauth_request')}</option>
                <option value="Priorauth_response">{translate('hcpNphiesPortalApp.EventCodingEnum.Priorauth_response')}</option>
                <option value="Claim_request">{translate('hcpNphiesPortalApp.EventCodingEnum.Claim_request')}</option>
                <option value="Claim_response">{translate('hcpNphiesPortalApp.EventCodingEnum.Claim_response')}</option>
                <option value="Batch_request">{translate('hcpNphiesPortalApp.EventCodingEnum.Batch_request')}</option>
                <option value="Status_check">{translate('hcpNphiesPortalApp.EventCodingEnum.Status_check')}</option>
                <option value="Status_response">{translate('hcpNphiesPortalApp.EventCodingEnum.Status_response')}</option>
                <option value="Cancel_request">{translate('hcpNphiesPortalApp.EventCodingEnum.Cancel_request')}</option>
                <option value="Cancel_response">{translate('hcpNphiesPortalApp.EventCodingEnum.Cancel_response')}</option>
                <option value="Payment_notice">{translate('hcpNphiesPortalApp.EventCodingEnum.Payment_notice')}</option>
                <option value="Payment_reconciliation">{translate('hcpNphiesPortalApp.EventCodingEnum.Payment_reconciliation')}</option>
                <option value="Communication_request">{translate('hcpNphiesPortalApp.EventCodingEnum.Communication_request')}</option>
                <option value="Communication">{translate('hcpNphiesPortalApp.EventCodingEnum.Communication')}</option>
                <option value="Acknowledgement">{translate('hcpNphiesPortalApp.EventCodingEnum.Acknowledgement')}</option>
                <option value="Poll_request">{translate('hcpNphiesPortalApp.EventCodingEnum.Poll_request')}</option>
                <option value="Poll_response">{translate('hcpNphiesPortalApp.EventCodingEnum.Poll_response')}</option>
                <option value="Nullify_request">{translate('hcpNphiesPortalApp.EventCodingEnum.Nullify_request')}</option>
                <option value="Nullify_response">{translate('hcpNphiesPortalApp.EventCodingEnum.Nullify_response')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.taskInput.inputCount')}
                id="task-input-inputCount"
                name="inputCount"
                data-cy="inputCount"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.taskInput.inputStart')}
                id="task-input-inputStart"
                name="inputStart"
                data-cy="inputStart"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.taskInput.inputEnd')}
                id="task-input-inputEnd"
                name="inputEnd"
                data-cy="inputEnd"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.taskInput.inputLineItem')}
                id="task-input-inputLineItem"
                name="inputLineItem"
                data-cy="inputLineItem"
                type="text"
              />
              <ValidatedField
                id="task-input-inputOrigResponse"
                name="inputOrigResponseId"
                data-cy="inputOrigResponse"
                label={translate('hcpNphiesPortalApp.taskInput.inputOrigResponse')}
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
                id="task-input-task"
                name="taskId"
                data-cy="task"
                label={translate('hcpNphiesPortalApp.taskInput.task')}
                type="select"
              >
                <option value="" key="0" />
                {tasks
                  ? tasks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/task-input" replace color="info">
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

export default TaskInputUpdate;
