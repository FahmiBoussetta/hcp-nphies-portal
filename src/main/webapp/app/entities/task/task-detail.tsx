import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './task.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TaskDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const taskEntity = useAppSelector(state => state.task.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="taskDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.task.detail.title">Task</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{taskEntity.id}</dd>
          <dt>
            <span id="guid">
              <Translate contentKey="hcpNphiesPortalApp.task.guid">Guid</Translate>
            </span>
          </dt>
          <dd>{taskEntity.guid}</dd>
          <dt>
            <span id="isQueued">
              <Translate contentKey="hcpNphiesPortalApp.task.isQueued">Is Queued</Translate>
            </span>
          </dt>
          <dd>{taskEntity.isQueued ? 'true' : 'false'}</dd>
          <dt>
            <span id="parsed">
              <Translate contentKey="hcpNphiesPortalApp.task.parsed">Parsed</Translate>
            </span>
          </dt>
          <dd>{taskEntity.parsed}</dd>
          <dt>
            <span id="identifier">
              <Translate contentKey="hcpNphiesPortalApp.task.identifier">Identifier</Translate>
            </span>
          </dt>
          <dd>{taskEntity.identifier}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="hcpNphiesPortalApp.task.code">Code</Translate>
            </span>
          </dt>
          <dd>{taskEntity.code}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="hcpNphiesPortalApp.task.description">Description</Translate>
            </span>
          </dt>
          <dd>{taskEntity.description}</dd>
          <dt>
            <span id="focus">
              <Translate contentKey="hcpNphiesPortalApp.task.focus">Focus</Translate>
            </span>
          </dt>
          <dd>{taskEntity.focus}</dd>
          <dt>
            <span id="reasonCode">
              <Translate contentKey="hcpNphiesPortalApp.task.reasonCode">Reason Code</Translate>
            </span>
          </dt>
          <dd>{taskEntity.reasonCode}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.task.requester">Requester</Translate>
          </dt>
          <dd>{taskEntity.requester ? taskEntity.requester.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.task.owner">Owner</Translate>
          </dt>
          <dd>{taskEntity.owner ? taskEntity.owner.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/task" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/task/${taskEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TaskDetail;
