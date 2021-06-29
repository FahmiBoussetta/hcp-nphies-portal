import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './claim-error-messages.reducer';
import { IClaimErrorMessages } from 'app/shared/model/claim-error-messages.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ClaimErrorMessages = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const claimErrorMessagesList = useAppSelector(state => state.claimErrorMessages.entities);
  const loading = useAppSelector(state => state.claimErrorMessages.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="claim-error-messages-heading" data-cy="ClaimErrorMessagesHeading">
        <Translate contentKey="hcpNphiesPortalApp.claimErrorMessages.home.title">Claim Error Messages</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.claimErrorMessages.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.claimErrorMessages.home.createLabel">Create new Claim Error Messages</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {claimErrorMessagesList && claimErrorMessagesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.claimErrorMessages.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.claimErrorMessages.message">Message</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.claimErrorMessages.claim">Claim</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {claimErrorMessagesList.map((claimErrorMessages, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${claimErrorMessages.id}`} color="link" size="sm">
                      {claimErrorMessages.id}
                    </Button>
                  </td>
                  <td>{claimErrorMessages.message}</td>
                  <td>
                    {claimErrorMessages.claim ? <Link to={`claim/${claimErrorMessages.claim.id}`}>{claimErrorMessages.claim.id}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${claimErrorMessages.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${claimErrorMessages.id}/edit`}
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
                        to={`${match.url}/${claimErrorMessages.id}/delete`}
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
              <Translate contentKey="hcpNphiesPortalApp.claimErrorMessages.home.notFound">No Claim Error Messages found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ClaimErrorMessages;
