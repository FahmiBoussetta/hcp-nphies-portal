import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './task-output.reducer';
import { ITaskOutput } from 'app/shared/model/task-output.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TaskOutput = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const taskOutputList = useAppSelector(state => state.taskOutput.entities);
  const loading = useAppSelector(state => state.taskOutput.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="task-output-heading" data-cy="TaskOutputHeading">
        <Translate contentKey="hcpNphiesPortalApp.taskOutput.home.title">Task Outputs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.taskOutput.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.taskOutput.home.createLabel">Create new Task Output</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {taskOutputList && taskOutputList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.taskOutput.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.taskOutput.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.taskOutput.errorOutput">Error Output</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.taskOutput.response">Response</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.taskOutput.taskResponse">Task Response</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {taskOutputList.map((taskOutput, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${taskOutput.id}`} color="link" size="sm">
                      {taskOutput.id}
                    </Button>
                  </td>
                  <td>{taskOutput.status}</td>
                  <td>{taskOutput.errorOutput}</td>
                  <td>
                    {taskOutput.response ? <Link to={`reference-identifier/${taskOutput.response.id}`}>{taskOutput.response.id}</Link> : ''}
                  </td>
                  <td>
                    {taskOutput.taskResponse ? (
                      <Link to={`task-response/${taskOutput.taskResponse.id}`}>{taskOutput.taskResponse.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${taskOutput.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${taskOutput.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${taskOutput.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hcpNphiesPortalApp.taskOutput.home.notFound">No Task Outputs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default TaskOutput;
