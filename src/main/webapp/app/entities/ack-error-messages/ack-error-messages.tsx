import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './ack-error-messages.reducer';
import { IAckErrorMessages } from 'app/shared/model/ack-error-messages.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AckErrorMessages = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const ackErrorMessagesList = useAppSelector(state => state.ackErrorMessages.entities);
  const loading = useAppSelector(state => state.ackErrorMessages.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="ack-error-messages-heading" data-cy="AckErrorMessagesHeading">
        <Translate contentKey="hcpNphiesPortalApp.ackErrorMessages.home.title">Ack Error Messages</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.ackErrorMessages.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.ackErrorMessages.home.createLabel">Create new Ack Error Messages</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ackErrorMessagesList && ackErrorMessagesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.ackErrorMessages.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.ackErrorMessages.message">Message</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.ackErrorMessages.acknowledgement">Acknowledgement</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ackErrorMessagesList.map((ackErrorMessages, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${ackErrorMessages.id}`} color="link" size="sm">
                      {ackErrorMessages.id}
                    </Button>
                  </td>
                  <td>{ackErrorMessages.message}</td>
                  <td>
                    {ackErrorMessages.acknowledgement ? (
                      <Link to={`acknowledgement/${ackErrorMessages.acknowledgement.id}`}>{ackErrorMessages.acknowledgement.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${ackErrorMessages.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${ackErrorMessages.id}/edit`}
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
                        to={`${match.url}/${ackErrorMessages.id}/delete`}
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
              <Translate contentKey="hcpNphiesPortalApp.ackErrorMessages.home.notFound">No Ack Error Messages found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default AckErrorMessages;
