import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './givens.reducer';
import { IGivens } from 'app/shared/model/givens.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Givens = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const givensList = useAppSelector(state => state.givens.entities);
  const loading = useAppSelector(state => state.givens.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="givens-heading" data-cy="GivensHeading">
        <Translate contentKey="hcpNphiesPortalApp.givens.home.title">Givens</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.givens.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.givens.home.createLabel">Create new Givens</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {givensList && givensList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.givens.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.givens.given">Given</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.givens.prefix">Prefix</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.givens.suffix">Suffix</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.givens.textName">Text Name</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.givens.human">Human</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {givensList.map((givens, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${givens.id}`} color="link" size="sm">
                      {givens.id}
                    </Button>
                  </td>
                  <td>{givens.given}</td>
                  <td>{givens.prefix}</td>
                  <td>{givens.suffix}</td>
                  <td>{givens.textName}</td>
                  <td>{givens.human ? <Link to={`human-name/${givens.human.id}`}>{givens.human.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${givens.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${givens.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${givens.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hcpNphiesPortalApp.givens.home.notFound">No Givens found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Givens;
