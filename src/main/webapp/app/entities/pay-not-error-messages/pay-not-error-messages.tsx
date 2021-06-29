import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './pay-not-error-messages.reducer';
import { IPayNotErrorMessages } from 'app/shared/model/pay-not-error-messages.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PayNotErrorMessages = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const payNotErrorMessagesList = useAppSelector(state => state.payNotErrorMessages.entities);
  const loading = useAppSelector(state => state.payNotErrorMessages.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="pay-not-error-messages-heading" data-cy="PayNotErrorMessagesHeading">
        <Translate contentKey="hcpNphiesPortalApp.payNotErrorMessages.home.title">Pay Not Error Messages</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.payNotErrorMessages.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.payNotErrorMessages.home.createLabel">Create new Pay Not Error Messages</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {payNotErrorMessagesList && payNotErrorMessagesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.payNotErrorMessages.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.payNotErrorMessages.message">Message</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.payNotErrorMessages.paymentNotice">Payment Notice</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {payNotErrorMessagesList.map((payNotErrorMessages, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${payNotErrorMessages.id}`} color="link" size="sm">
                      {payNotErrorMessages.id}
                    </Button>
                  </td>
                  <td>{payNotErrorMessages.message}</td>
                  <td>
                    {payNotErrorMessages.paymentNotice ? (
                      <Link to={`payment-notice/${payNotErrorMessages.paymentNotice.id}`}>{payNotErrorMessages.paymentNotice.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${payNotErrorMessages.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${payNotErrorMessages.id}/edit`}
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
                        to={`${match.url}/${payNotErrorMessages.id}/delete`}
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
              <Translate contentKey="hcpNphiesPortalApp.payNotErrorMessages.home.notFound">No Pay Not Error Messages found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default PayNotErrorMessages;
