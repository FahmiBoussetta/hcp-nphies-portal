import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './practitioner.reducer';
import { IPractitioner } from 'app/shared/model/practitioner.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Practitioner = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const practitionerList = useAppSelector(state => state.practitioner.entities);
  const loading = useAppSelector(state => state.practitioner.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="practitioner-heading" data-cy="PractitionerHeading">
        <Translate contentKey="hcpNphiesPortalApp.practitioner.home.title">Practitioners</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.practitioner.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.practitioner.home.createLabel">Create new Practitioner</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {practitionerList && practitionerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.practitioner.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.practitioner.guid">Guid</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.practitioner.forceId">Force Id</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.practitioner.practitionerLicense">Practitioner License</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.practitioner.gender">Gender</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {practitionerList.map((practitioner, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${practitioner.id}`} color="link" size="sm">
                      {practitioner.id}
                    </Button>
                  </td>
                  <td>{practitioner.guid}</td>
                  <td>{practitioner.forceId}</td>
                  <td>{practitioner.practitionerLicense}</td>
                  <td>
                    <Translate contentKey={`hcpNphiesPortalApp.AdministrativeGenderEnum.${practitioner.gender}`} />
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${practitioner.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${practitioner.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${practitioner.id}/delete`}
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
              <Translate contentKey="hcpNphiesPortalApp.practitioner.home.notFound">No Practitioners found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Practitioner;
