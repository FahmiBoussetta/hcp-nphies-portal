import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './address.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AddressDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const addressEntity = useAppSelector(state => state.address.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="addressDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.address.detail.title">Address</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{addressEntity.id}</dd>
          <dt>
            <span id="addressLine">
              <Translate contentKey="hcpNphiesPortalApp.address.addressLine">Address Line</Translate>
            </span>
          </dt>
          <dd>{addressEntity.addressLine}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="hcpNphiesPortalApp.address.city">City</Translate>
            </span>
          </dt>
          <dd>{addressEntity.city}</dd>
          <dt>
            <span id="district">
              <Translate contentKey="hcpNphiesPortalApp.address.district">District</Translate>
            </span>
          </dt>
          <dd>{addressEntity.district}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="hcpNphiesPortalApp.address.state">State</Translate>
            </span>
          </dt>
          <dd>{addressEntity.state}</dd>
          <dt>
            <span id="postalCode">
              <Translate contentKey="hcpNphiesPortalApp.address.postalCode">Postal Code</Translate>
            </span>
          </dt>
          <dd>{addressEntity.postalCode}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="hcpNphiesPortalApp.address.country">Country</Translate>
            </span>
          </dt>
          <dd>{addressEntity.country}</dd>
        </dl>
        <Button tag={Link} to="/address" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/address/${addressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AddressDetail;
