import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './organization.reducer';
import { IOrganization } from 'app/shared/model/organization.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Organization = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const organizationList = useAppSelector(state => state.organization.entities);
  const loading = useAppSelector(state => state.organization.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="organization-heading" data-cy="OrganizationHeading">
        <Translate contentKey="hcpNphiesPortalApp.organization.home.title">Organizations</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.organization.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.organization.home.createLabel">Create new Organization</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {organizationList && organizationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.organization.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.organization.guid">Guid</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.organization.forceId">Force Id</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.organization.organizationLicense">Organization License</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.organization.baseUrl">Base Url</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.organization.organizationType">Organization Type</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.organization.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.organization.address">Address</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {organizationList.map((organization, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${organization.id}`} color="link" size="sm">
                      {organization.id}
                    </Button>
                  </td>
                  <td>{organization.guid}</td>
                  <td>{organization.forceId}</td>
                  <td>{organization.organizationLicense}</td>
                  <td>{organization.baseUrl}</td>
                  <td>
                    <Translate contentKey={`hcpNphiesPortalApp.OrganizationTypeEnum.${organization.organizationType}`} />
                  </td>
                  <td>{organization.name}</td>
                  <td>{organization.address ? <Link to={`address/${organization.address.id}`}>{organization.address.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${organization.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${organization.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${organization.id}/delete`}
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
              <Translate contentKey="hcpNphiesPortalApp.organization.home.notFound">No Organizations found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Organization;
