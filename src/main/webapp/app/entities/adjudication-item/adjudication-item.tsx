import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './adjudication-item.reducer';
import { IAdjudicationItem } from 'app/shared/model/adjudication-item.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AdjudicationItem = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const adjudicationItemList = useAppSelector(state => state.adjudicationItem.entities);
  const loading = useAppSelector(state => state.adjudicationItem.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="adjudication-item-heading" data-cy="AdjudicationItemHeading">
        <Translate contentKey="hcpNphiesPortalApp.adjudicationItem.home.title">Adjudication Items</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.adjudicationItem.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.adjudicationItem.home.createLabel">Create new Adjudication Item</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {adjudicationItemList && adjudicationItemList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.adjudicationItem.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.adjudicationItem.outcome">Outcome</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.adjudicationItem.sequence">Sequence</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.adjudicationItem.claimResponse">Claim Response</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {adjudicationItemList.map((adjudicationItem, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${adjudicationItem.id}`} color="link" size="sm">
                      {adjudicationItem.id}
                    </Button>
                  </td>
                  <td>{adjudicationItem.outcome}</td>
                  <td>{adjudicationItem.sequence}</td>
                  <td>
                    {adjudicationItem.claimResponse ? (
                      <Link to={`claim-response/${adjudicationItem.claimResponse.id}`}>{adjudicationItem.claimResponse.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${adjudicationItem.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${adjudicationItem.id}/edit`}
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
                        to={`${match.url}/${adjudicationItem.id}/delete`}
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
              <Translate contentKey="hcpNphiesPortalApp.adjudicationItem.home.notFound">No Adjudication Items found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default AdjudicationItem;
