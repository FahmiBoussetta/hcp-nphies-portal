import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './payload.reducer';
import { IPayload } from 'app/shared/model/payload.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Payload = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const payloadList = useAppSelector(state => state.payload.entities);
  const loading = useAppSelector(state => state.payload.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="payload-heading" data-cy="PayloadHeading">
        <Translate contentKey="hcpNphiesPortalApp.payload.home.title">Payloads</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.payload.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.payload.home.createLabel">Create new Payload</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {payloadList && payloadList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.payload.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.payload.contentString">Content String</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.payload.contentAttachment">Content Attachment</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.payload.contentReference">Content Reference</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.payload.communication">Communication</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.payload.communicationRequest">Communication Request</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {payloadList.map((payload, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${payload.id}`} color="link" size="sm">
                      {payload.id}
                    </Button>
                  </td>
                  <td>{payload.contentString}</td>
                  <td>
                    {payload.contentAttachment ? (
                      <Link to={`attachment/${payload.contentAttachment.id}`}>{payload.contentAttachment.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {payload.contentReference ? (
                      <Link to={`reference-identifier/${payload.contentReference.id}`}>{payload.contentReference.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {payload.communication ? <Link to={`communication/${payload.communication.id}`}>{payload.communication.id}</Link> : ''}
                  </td>
                  <td>
                    {payload.communicationRequest ? (
                      <Link to={`communication-request/${payload.communicationRequest.id}`}>{payload.communicationRequest.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${payload.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${payload.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${payload.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hcpNphiesPortalApp.payload.home.notFound">No Payloads found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Payload;
