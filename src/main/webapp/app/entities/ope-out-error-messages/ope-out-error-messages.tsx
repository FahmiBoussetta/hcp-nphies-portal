import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './ope-out-error-messages.reducer';
import { IOpeOutErrorMessages } from 'app/shared/model/ope-out-error-messages.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const OpeOutErrorMessages = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const opeOutErrorMessagesList = useAppSelector(state => state.opeOutErrorMessages.entities);
  const loading = useAppSelector(state => state.opeOutErrorMessages.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="ope-out-error-messages-heading" data-cy="OpeOutErrorMessagesHeading">
        <Translate contentKey="hcpNphiesPortalApp.opeOutErrorMessages.home.title">Ope Out Error Messages</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.opeOutErrorMessages.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.opeOutErrorMessages.home.createLabel">Create new Ope Out Error Messages</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {opeOutErrorMessagesList && opeOutErrorMessagesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.opeOutErrorMessages.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.opeOutErrorMessages.message">Message</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.opeOutErrorMessages.operationOutcome">Operation Outcome</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {opeOutErrorMessagesList.map((opeOutErrorMessages, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${opeOutErrorMessages.id}`} color="link" size="sm">
                      {opeOutErrorMessages.id}
                    </Button>
                  </td>
                  <td>{opeOutErrorMessages.message}</td>
                  <td>
                    {opeOutErrorMessages.operationOutcome ? (
                      <Link to={`operation-outcome/${opeOutErrorMessages.operationOutcome.id}`}>
                        {opeOutErrorMessages.operationOutcome.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${opeOutErrorMessages.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${opeOutErrorMessages.id}/edit`}
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
                        to={`${match.url}/${opeOutErrorMessages.id}/delete`}
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
              <Translate contentKey="hcpNphiesPortalApp.opeOutErrorMessages.home.notFound">No Ope Out Error Messages found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default OpeOutErrorMessages;
