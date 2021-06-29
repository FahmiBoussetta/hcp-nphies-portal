import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IOrganization } from 'app/shared/model/organization.model';
import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { getEntity, updateEntity, createEntity, reset } from './task.reducer';
import { ITask } from 'app/shared/model/task.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TaskUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const organizations = useAppSelector(state => state.organization.entities);
  const taskEntity = useAppSelector(state => state.task.entity);
  const loading = useAppSelector(state => state.task.loading);
  const updating = useAppSelector(state => state.task.updating);
  const updateSuccess = useAppSelector(state => state.task.updateSuccess);

  const handleClose = () => {
    props.history.push('/task');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getOrganizations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...taskEntity,
      ...values,
      requester: organizations.find(it => it.id.toString() === values.requesterId.toString()),
      owner: organizations.find(it => it.id.toString() === values.ownerId.toString()),
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
          ...taskEntity,
          code: 'Cancel',
          reasonCode: 'WI',
          requesterId: taskEntity?.requester?.id,
          ownerId: taskEntity?.owner?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.task.home.createOrEditLabel" data-cy="TaskCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.task.home.createOrEditLabel">Create or edit a Task</Translate>
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
                  id="task-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('hcpNphiesPortalApp.task.guid')} id="task-guid" name="guid" data-cy="guid" type="text" />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.task.isQueued')}
                id="task-isQueued"
                name="isQueued"
                data-cy="isQueued"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.task.parsed')}
                id="task-parsed"
                name="parsed"
                data-cy="parsed"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.task.identifier')}
                id="task-identifier"
                name="identifier"
                data-cy="identifier"
                type="text"
              />
              <ValidatedField label={translate('hcpNphiesPortalApp.task.code')} id="task-code" name="code" data-cy="code" type="select">
                <option value="Cancel">{translate('hcpNphiesPortalApp.TaskCodeEnum.Cancel')}</option>
                <option value="Nullify">{translate('hcpNphiesPortalApp.TaskCodeEnum.Nullify')}</option>
                <option value="Poll">{translate('hcpNphiesPortalApp.TaskCodeEnum.Poll')}</option>
                <option value="Release">{translate('hcpNphiesPortalApp.TaskCodeEnum.Release')}</option>
                <option value="Reprocess">{translate('hcpNphiesPortalApp.TaskCodeEnum.Reprocess')}</option>
                <option value="Status">{translate('hcpNphiesPortalApp.TaskCodeEnum.Status')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.task.description')}
                id="task-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField label={translate('hcpNphiesPortalApp.task.focus')} id="task-focus" name="focus" data-cy="focus" type="text" />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.task.reasonCode')}
                id="task-reasonCode"
                name="reasonCode"
                data-cy="reasonCode"
                type="select"
              >
                <option value="WI">{translate('hcpNphiesPortalApp.TaskReasonCodeEnum.WI')}</option>
                <option value="NP">{translate('hcpNphiesPortalApp.TaskReasonCodeEnum.NP')}</option>
                <option value="TAS">{translate('hcpNphiesPortalApp.TaskReasonCodeEnum.TAS')}</option>
              </ValidatedField>
              <ValidatedField
                id="task-requester"
                name="requesterId"
                data-cy="requester"
                label={translate('hcpNphiesPortalApp.task.requester')}
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
                id="task-owner"
                name="ownerId"
                data-cy="owner"
                label={translate('hcpNphiesPortalApp.task.owner')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/task" replace color="info">
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

export default TaskUpdate;
