import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './communication-request.reducer';
import { ICommunicationRequest } from 'app/shared/model/communication-request.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CommunicationRequest = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const communicationRequestList = useAppSelector(state => state.communicationRequest.entities);
  const loading = useAppSelector(state => state.communicationRequest.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="communication-request-heading" data-cy="CommunicationRequestHeading">
        <Translate contentKey="hcpNphiesPortalApp.communicationRequest.home.title">Communication Requests</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.communicationRequest.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.communicationRequest.home.createLabel">Create new Communication Request</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {communicationRequestList && communicationRequestList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.communicationRequest.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.communicationRequest.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.communicationRequest.system">System</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.communicationRequest.parsed">Parsed</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.communicationRequest.limitDate">Limit Date</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.communicationRequest.subject">Subject</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.communicationRequest.about">About</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.communicationRequest.sender">Sender</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.communicationRequest.communication">Communication</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {communicationRequestList.map((communicationRequest, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${communicationRequest.id}`} color="link" size="sm">
                      {communicationRequest.id}
                    </Button>
                  </td>
                  <td>{communicationRequest.value}</td>
                  <td>{communicationRequest.system}</td>
                  <td>{communicationRequest.parsed}</td>
                  <td>
                    {communicationRequest.limitDate ? (
                      <TextFormat type="date" value={communicationRequest.limitDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {communicationRequest.subject ? (
                      <Link to={`patient/${communicationRequest.subject.id}`}>{communicationRequest.subject.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {communicationRequest.about ? (
                      <Link to={`claim/${communicationRequest.about.id}`}>{communicationRequest.about.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {communicationRequest.sender ? (
                      <Link to={`organization/${communicationRequest.sender.id}`}>{communicationRequest.sender.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {communicationRequest.communication ? (
                      <Link to={`communication/${communicationRequest.communication.id}`}>{communicationRequest.communication.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${communicationRequest.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${communicationRequest.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${communicationRequest.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="hcpNphiesPortalApp.communicationRequest.home.notFound">No Communication Requests found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default CommunicationRequest;
