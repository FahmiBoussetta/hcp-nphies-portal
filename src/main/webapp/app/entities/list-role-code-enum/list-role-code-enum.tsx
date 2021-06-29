import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './list-role-code-enum.reducer';
import { IListRoleCodeEnum } from 'app/shared/model/list-role-code-enum.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ListRoleCodeEnum = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const listRoleCodeEnumList = useAppSelector(state => state.listRoleCodeEnum.entities);
  const loading = useAppSelector(state => state.listRoleCodeEnum.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="list-role-code-enum-heading" data-cy="ListRoleCodeEnumHeading">
        <Translate contentKey="hcpNphiesPortalApp.listRoleCodeEnum.home.title">List Role Code Enums</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.listRoleCodeEnum.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.listRoleCodeEnum.home.createLabel">Create new List Role Code Enum</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {listRoleCodeEnumList && listRoleCodeEnumList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.listRoleCodeEnum.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.listRoleCodeEnum.r">R</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.listRoleCodeEnum.practitionerRole">Practitioner Role</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {listRoleCodeEnumList.map((listRoleCodeEnum, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${listRoleCodeEnum.id}`} color="link" size="sm">
                      {listRoleCodeEnum.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`hcpNphiesPortalApp.RoleCodeEnum.${listRoleCodeEnum.r}`} />
                  </td>
                  <td>
                    {listRoleCodeEnum.practitionerRole ? (
                      <Link to={`practitioner-role/${listRoleCodeEnum.practitionerRole.id}`}>{listRoleCodeEnum.practitionerRole.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${listRoleCodeEnum.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${listRoleCodeEnum.id}/edit`}
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
                        to={`${match.url}/${listRoleCodeEnum.id}/delete`}
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
              <Translate contentKey="hcpNphiesPortalApp.listRoleCodeEnum.home.notFound">No List Role Code Enums found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ListRoleCodeEnum;
