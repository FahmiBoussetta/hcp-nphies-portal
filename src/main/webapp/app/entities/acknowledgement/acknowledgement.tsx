import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './acknowledgement.reducer';
import { IAcknowledgement } from 'app/shared/model/acknowledgement.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Acknowledgement = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const acknowledgementList = useAppSelector(state => state.acknowledgement.entities);
  const loading = useAppSelector(state => state.acknowledgement.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="acknowledgement-heading" data-cy="AcknowledgementHeading">
        <Translate contentKey="hcpNphiesPortalApp.acknowledgement.home.title">Acknowledgements</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.acknowledgement.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.acknowledgement.home.createLabel">Create new Acknowledgement</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {acknowledgementList && acknowledgementList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.acknowledgement.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.acknowledgement.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.acknowledgement.system">System</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.acknowledgement.parsed">Parsed</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {acknowledgementList.map((acknowledgement, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${acknowledgement.id}`} color="link" size="sm">
                      {acknowledgement.id}
                    </Button>
                  </td>
                  <td>{acknowledgement.value}</td>
                  <td>{acknowledgement.system}</td>
                  <td>{acknowledgement.parsed}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${acknowledgement.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${acknowledgement.id}/edit`}
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
                        to={`${match.url}/${acknowledgement.id}/delete`}
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
              <Translate contentKey="hcpNphiesPortalApp.acknowledgement.home.notFound">No Acknowledgements found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Acknowledgement;
