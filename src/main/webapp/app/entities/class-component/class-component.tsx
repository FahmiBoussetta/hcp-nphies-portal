import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './class-component.reducer';
import { IClassComponent } from 'app/shared/model/class-component.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ClassComponent = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const classComponentList = useAppSelector(state => state.classComponent.entities);
  const loading = useAppSelector(state => state.classComponent.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="class-component-heading" data-cy="ClassComponentHeading">
        <Translate contentKey="hcpNphiesPortalApp.classComponent.home.title">Class Components</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.classComponent.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.classComponent.home.createLabel">Create new Class Component</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {classComponentList && classComponentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.classComponent.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.classComponent.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.classComponent.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.classComponent.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.classComponent.coverage">Coverage</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {classComponentList.map((classComponent, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${classComponent.id}`} color="link" size="sm">
                      {classComponent.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`hcpNphiesPortalApp.ClassTypeEnum.${classComponent.type}`} />
                  </td>
                  <td>{classComponent.value}</td>
                  <td>{classComponent.name}</td>
                  <td>
                    {classComponent.coverage ? <Link to={`coverage/${classComponent.coverage.id}`}>{classComponent.coverage.id}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${classComponent.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${classComponent.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${classComponent.id}/delete`}
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
              <Translate contentKey="hcpNphiesPortalApp.classComponent.home.notFound">No Class Components found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ClassComponent;
