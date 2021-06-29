import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './task.reducer';
import { ITask } from 'app/shared/model/task.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Task = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const taskList = useAppSelector(state => state.task.entities);
  const loading = useAppSelector(state => state.task.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="task-heading" data-cy="TaskHeading">
        <Translate contentKey="hcpNphiesPortalApp.task.home.title">Tasks</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.task.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.task.home.createLabel">Create new Task</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {taskList && taskList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.task.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.task.guid">Guid</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.task.isQueued">Is Queued</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.task.parsed">Parsed</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.task.identifier">Identifier</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.task.code">Code</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.task.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.task.focus">Focus</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.task.reasonCode">Reason Code</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.task.requester">Requester</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.task.owner">Owner</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {taskList.map((task, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${task.id}`} color="link" size="sm">
                      {task.id}
                    </Button>
                  </td>
                  <td>{task.guid}</td>
                  <td>{task.isQueued ? 'true' : 'false'}</td>
                  <td>{task.parsed}</td>
                  <td>{task.identifier}</td>
                  <td>
                    <Translate contentKey={`hcpNphiesPortalApp.TaskCodeEnum.${task.code}`} />
                  </td>
                  <td>{task.description}</td>
                  <td>{task.focus}</td>
                  <td>
                    <Translate contentKey={`hcpNphiesPortalApp.TaskReasonCodeEnum.${task.reasonCode}`} />
                  </td>
                  <td>{task.requester ? <Link to={`organization/${task.requester.id}`}>{task.requester.id}</Link> : ''}</td>
                  <td>{task.owner ? <Link to={`organization/${task.owner.id}`}>{task.owner.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${task.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${task.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${task.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="hcpNphiesPortalApp.task.home.notFound">No Tasks found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Task;
