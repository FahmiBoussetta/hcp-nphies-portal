import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './care-team.reducer';
import { ICareTeam } from 'app/shared/model/care-team.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CareTeam = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const careTeamList = useAppSelector(state => state.careTeam.entities);
  const loading = useAppSelector(state => state.careTeam.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="care-team-heading" data-cy="CareTeamHeading">
        <Translate contentKey="hcpNphiesPortalApp.careTeam.home.title">Care Teams</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.careTeam.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.careTeam.home.createLabel">Create new Care Team</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {careTeamList && careTeamList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.careTeam.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.careTeam.sequence">Sequence</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.careTeam.role">Role</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.careTeam.provider">Provider</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.careTeam.providerRole">Provider Role</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.careTeam.claim">Claim</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {careTeamList.map((careTeam, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${careTeam.id}`} color="link" size="sm">
                      {careTeam.id}
                    </Button>
                  </td>
                  <td>{careTeam.sequence}</td>
                  <td>
                    <Translate contentKey={`hcpNphiesPortalApp.CareTeamRoleEnum.${careTeam.role}`} />
                  </td>
                  <td>{careTeam.provider ? <Link to={`practitioner/${careTeam.provider.id}`}>{careTeam.provider.id}</Link> : ''}</td>
                  <td>
                    {careTeam.providerRole ? (
                      <Link to={`practitioner-role/${careTeam.providerRole.id}`}>{careTeam.providerRole.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{careTeam.claim ? <Link to={`claim/${careTeam.claim.id}`}>{careTeam.claim.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${careTeam.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${careTeam.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${careTeam.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hcpNphiesPortalApp.careTeam.home.notFound">No Care Teams found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default CareTeam;
