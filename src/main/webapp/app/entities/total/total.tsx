import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './total.reducer';
import { ITotal } from 'app/shared/model/total.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Total = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const totalList = useAppSelector(state => state.total.entities);
  const loading = useAppSelector(state => state.total.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="total-heading" data-cy="TotalHeading">
        <Translate contentKey="hcpNphiesPortalApp.total.home.title">Totals</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.total.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.total.home.createLabel">Create new Total</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {totalList && totalList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.total.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.total.category">Category</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.total.amount">Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.total.claimResponse">Claim Response</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {totalList.map((total, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${total.id}`} color="link" size="sm">
                      {total.id}
                    </Button>
                  </td>
                  <td>{total.category}</td>
                  <td>{total.amount}</td>
                  <td>
                    {total.claimResponse ? <Link to={`claim-response/${total.claimResponse.id}`}>{total.claimResponse.id}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${total.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${total.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${total.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hcpNphiesPortalApp.total.home.notFound">No Totals found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Total;
