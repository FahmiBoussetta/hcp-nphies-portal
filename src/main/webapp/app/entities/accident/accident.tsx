import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './accident.reducer';
import { IAccident } from 'app/shared/model/accident.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Accident = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const accidentList = useAppSelector(state => state.accident.entities);
  const loading = useAppSelector(state => state.accident.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="accident-heading" data-cy="AccidentHeading">
        <Translate contentKey="hcpNphiesPortalApp.accident.home.title">Accidents</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.accident.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.accident.home.createLabel">Create new Accident</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {accidentList && accidentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.accident.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.accident.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.accident.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.accident.location">Location</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {accidentList.map((accident, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${accident.id}`} color="link" size="sm">
                      {accident.id}
                    </Button>
                  </td>
                  <td>{accident.date ? <TextFormat type="date" value={accident.date} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    <Translate contentKey={`hcpNphiesPortalApp.AccidentTypeEnum.${accident.type}`} />
                  </td>
                  <td>{accident.location ? <Link to={`address/${accident.location.id}`}>{accident.location.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${accident.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${accident.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${accident.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hcpNphiesPortalApp.accident.home.notFound">No Accidents found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Accident;
