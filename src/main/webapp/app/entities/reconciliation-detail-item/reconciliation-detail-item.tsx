import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './reconciliation-detail-item.reducer';
import { IReconciliationDetailItem } from 'app/shared/model/reconciliation-detail-item.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ReconciliationDetailItem = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const reconciliationDetailItemList = useAppSelector(state => state.reconciliationDetailItem.entities);
  const loading = useAppSelector(state => state.reconciliationDetailItem.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="reconciliation-detail-item-heading" data-cy="ReconciliationDetailItemHeading">
        <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.home.title">Reconciliation Detail Items</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.home.createLabel">
              Create new Reconciliation Detail Item
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {reconciliationDetailItemList && reconciliationDetailItemList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.identifier">Identifier</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.predecessor">Predecessor</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.amount">Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.request">Request</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.submitter">Submitter</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.response">Response</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.payee">Payee</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.paymentReconciliation">
                    Payment Reconciliation
                  </Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {reconciliationDetailItemList.map((reconciliationDetailItem, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${reconciliationDetailItem.id}`} color="link" size="sm">
                      {reconciliationDetailItem.id}
                    </Button>
                  </td>
                  <td>{reconciliationDetailItem.identifier}</td>
                  <td>{reconciliationDetailItem.predecessor}</td>
                  <td>{reconciliationDetailItem.type}</td>
                  <td>
                    {reconciliationDetailItem.date ? (
                      <TextFormat type="date" value={reconciliationDetailItem.date} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{reconciliationDetailItem.amount}</td>
                  <td>
                    {reconciliationDetailItem.request ? (
                      <Link to={`claim/${reconciliationDetailItem.request.id}`}>{reconciliationDetailItem.request.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {reconciliationDetailItem.submitter ? (
                      <Link to={`organization/${reconciliationDetailItem.submitter.id}`}>{reconciliationDetailItem.submitter.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {reconciliationDetailItem.response ? (
                      <Link to={`claim-response/${reconciliationDetailItem.response.id}`}>{reconciliationDetailItem.response.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {reconciliationDetailItem.payee ? (
                      <Link to={`organization/${reconciliationDetailItem.payee.id}`}>{reconciliationDetailItem.payee.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {reconciliationDetailItem.paymentReconciliation ? (
                      <Link to={`payment-reconciliation/${reconciliationDetailItem.paymentReconciliation.id}`}>
                        {reconciliationDetailItem.paymentReconciliation.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${reconciliationDetailItem.id}`}
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
                        to={`${match.url}/${reconciliationDetailItem.id}/edit`}
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
                        to={`${match.url}/${reconciliationDetailItem.id}/delete`}
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
              <Translate contentKey="hcpNphiesPortalApp.reconciliationDetailItem.home.notFound">
                No Reconciliation Detail Items found
              </Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ReconciliationDetailItem;
