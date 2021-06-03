import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IOrganization } from 'app/shared/model/organization.model';
import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { getEntity, updateEntity, createEntity, reset } from './task.reducer';
import { ITask } from 'app/shared/model/task.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITaskUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TaskUpdate = (props: ITaskUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { taskEntity, organizations, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/task');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getOrganizations();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...taskEntity,
        ...values,
        requester: organizations.find(it => it.id.toString() === values.requesterId.toString()),
        owner: organizations.find(it => it.id.toString() === values.ownerId.toString()),
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
            <AvForm model={isNew ? {} : taskEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="task-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="task-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="guidLabel" for="task-guid">
                  <Translate contentKey="hcpNphiesPortalApp.task.guid">Guid</Translate>
                </Label>
                <AvField id="task-guid" data-cy="guid" type="text" name="guid" />
              </AvGroup>
              <AvGroup check>
                <Label id="isQueuedLabel">
                  <AvInput id="task-isQueued" data-cy="isQueued" type="checkbox" className="form-check-input" name="isQueued" />
                  <Translate contentKey="hcpNphiesPortalApp.task.isQueued">Is Queued</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="parsedLabel" for="task-parsed">
                  <Translate contentKey="hcpNphiesPortalApp.task.parsed">Parsed</Translate>
                </Label>
                <AvField id="task-parsed" data-cy="parsed" type="text" name="parsed" />
              </AvGroup>
              <AvGroup>
                <Label id="identifierLabel" for="task-identifier">
                  <Translate contentKey="hcpNphiesPortalApp.task.identifier">Identifier</Translate>
                </Label>
                <AvField id="task-identifier" data-cy="identifier" type="text" name="identifier" />
              </AvGroup>
              <AvGroup>
                <Label id="codeLabel" for="task-code">
                  <Translate contentKey="hcpNphiesPortalApp.task.code">Code</Translate>
                </Label>
                <AvInput
                  id="task-code"
                  data-cy="code"
                  type="select"
                  className="form-control"
                  name="code"
                  value={(!isNew && taskEntity.code) || 'Cancel'}
                >
                  <option value="Cancel">{translate('hcpNphiesPortalApp.TaskCodeEnum.Cancel')}</option>
                  <option value="Nullify">{translate('hcpNphiesPortalApp.TaskCodeEnum.Nullify')}</option>
                  <option value="Poll">{translate('hcpNphiesPortalApp.TaskCodeEnum.Poll')}</option>
                  <option value="Release">{translate('hcpNphiesPortalApp.TaskCodeEnum.Release')}</option>
                  <option value="Reprocess">{translate('hcpNphiesPortalApp.TaskCodeEnum.Reprocess')}</option>
                  <option value="Status">{translate('hcpNphiesPortalApp.TaskCodeEnum.Status')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="task-description">
                  <Translate contentKey="hcpNphiesPortalApp.task.description">Description</Translate>
                </Label>
                <AvField id="task-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="focusLabel" for="task-focus">
                  <Translate contentKey="hcpNphiesPortalApp.task.focus">Focus</Translate>
                </Label>
                <AvField id="task-focus" data-cy="focus" type="text" name="focus" />
              </AvGroup>
              <AvGroup>
                <Label id="reasonCodeLabel" for="task-reasonCode">
                  <Translate contentKey="hcpNphiesPortalApp.task.reasonCode">Reason Code</Translate>
                </Label>
                <AvInput
                  id="task-reasonCode"
                  data-cy="reasonCode"
                  type="select"
                  className="form-control"
                  name="reasonCode"
                  value={(!isNew && taskEntity.reasonCode) || 'WI'}
                >
                  <option value="WI">{translate('hcpNphiesPortalApp.TaskReasonCodeEnum.WI')}</option>
                  <option value="NP">{translate('hcpNphiesPortalApp.TaskReasonCodeEnum.NP')}</option>
                  <option value="TAS">{translate('hcpNphiesPortalApp.TaskReasonCodeEnum.TAS')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="task-requester">
                  <Translate contentKey="hcpNphiesPortalApp.task.requester">Requester</Translate>
                </Label>
                <AvInput id="task-requester" data-cy="requester" type="select" className="form-control" name="requesterId">
                  <option value="" key="0" />
                  {organizations
                    ? organizations.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="task-owner">
                  <Translate contentKey="hcpNphiesPortalApp.task.owner">Owner</Translate>
                </Label>
                <AvInput id="task-owner" data-cy="owner" type="select" className="form-control" name="ownerId">
                  <option value="" key="0" />
                  {organizations
                    ? organizations.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/task" replace color="info">
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
  organizations: storeState.organization.entities,
  taskEntity: storeState.task.entity,
  loading: storeState.task.loading,
  updating: storeState.task.updating,
  updateSuccess: storeState.task.updateSuccess,
});

const mapDispatchToProps = {
  getOrganizations,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TaskUpdate);
