import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './human-name.reducer';
import { IHumanName } from 'app/shared/model/human-name.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const HumanName = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const humanNameList = useAppSelector(state => state.humanName.entities);
  const loading = useAppSelector(state => state.humanName.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="human-name-heading" data-cy="HumanNameHeading">
        <Translate contentKey="hcpNphiesPortalApp.humanName.home.title">Human Names</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.humanName.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.humanName.home.createLabel">Create new Human Name</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {humanNameList && humanNameList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.humanName.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.humanName.family">Family</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.humanName.patient">Patient</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.humanName.practitioner">Practitioner</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {humanNameList.map((humanName, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${humanName.id}`} color="link" size="sm">
                      {humanName.id}
                    </Button>
                  </td>
                  <td>{humanName.family}</td>
                  <td>{humanName.patient ? <Link to={`patient/${humanName.patient.id}`}>{humanName.patient.id}</Link> : ''}</td>
                  <td>
                    {humanName.practitioner ? (
                      <Link to={`practitioner/${humanName.practitioner.id}`}>{humanName.practitioner.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${humanName.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${humanName.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${humanName.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hcpNphiesPortalApp.humanName.home.notFound">No Human Names found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default HumanName;
