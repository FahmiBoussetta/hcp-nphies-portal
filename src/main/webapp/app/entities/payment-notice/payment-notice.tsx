import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './payment-notice.reducer';
import { IPaymentNotice } from 'app/shared/model/payment-notice.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PaymentNotice = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const paymentNoticeList = useAppSelector(state => state.paymentNotice.entities);
  const loading = useAppSelector(state => state.paymentNotice.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="payment-notice-heading" data-cy="PaymentNoticeHeading">
        <Translate contentKey="hcpNphiesPortalApp.paymentNotice.home.title">Payment Notices</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.paymentNotice.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.paymentNotice.home.createLabel">Create new Payment Notice</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {paymentNoticeList && paymentNoticeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.paymentNotice.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.paymentNotice.guid">Guid</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.paymentNotice.parsed">Parsed</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.paymentNotice.identifier">Identifier</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.paymentNotice.paymentDate">Payment Date</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.paymentNotice.amount">Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.paymentNotice.paymentStatus">Payment Status</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.paymentNotice.payment">Payment</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {paymentNoticeList.map((paymentNotice, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${paymentNotice.id}`} color="link" size="sm">
                      {paymentNotice.id}
                    </Button>
                  </td>
                  <td>{paymentNotice.guid}</td>
                  <td>{paymentNotice.parsed}</td>
                  <td>{paymentNotice.identifier}</td>
                  <td>
                    {paymentNotice.paymentDate ? (
                      <TextFormat type="date" value={paymentNotice.paymentDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{paymentNotice.amount}</td>
                  <td>
                    <Translate contentKey={`hcpNphiesPortalApp.PaymentStatusEnum.${paymentNotice.paymentStatus}`} />
                  </td>
                  <td>
                    {paymentNotice.payment ? (
                      <Link to={`payment-reconciliation/${paymentNotice.payment.id}`}>{paymentNotice.payment.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${paymentNotice.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${paymentNotice.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${paymentNotice.id}/delete`}
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
              <Translate contentKey="hcpNphiesPortalApp.paymentNotice.home.notFound">No Payment Notices found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default PaymentNotice;
