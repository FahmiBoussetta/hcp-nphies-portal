import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './related.reducer';
import { IRelated } from 'app/shared/model/related.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Related = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const relatedList = useAppSelector(state => state.related.entities);
  const loading = useAppSelector(state => state.related.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="related-heading" data-cy="RelatedHeading">
        <Translate contentKey="hcpNphiesPortalApp.related.home.title">Relateds</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.related.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.related.home.createLabel">Create new Related</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {relatedList && relatedList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.related.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.related.relationShip">Relation Ship</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.related.claimReference">Claim Reference</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.related.claim">Claim</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {relatedList.map((related, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${related.id}`} color="link" size="sm">
                      {related.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`hcpNphiesPortalApp.ClaimRelationshipEnum.${related.relationShip}`} />
                  </td>
                  <td>
                    {related.claimReference ? (
                      <Link to={`reference-identifier/${related.claimReference.id}`}>{related.claimReference.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{related.claim ? <Link to={`claim/${related.claim.id}`}>{related.claim.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${related.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${related.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${related.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hcpNphiesPortalApp.related.home.notFound">No Relateds found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Related;
