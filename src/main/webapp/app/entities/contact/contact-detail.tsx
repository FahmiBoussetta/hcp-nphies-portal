import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './contact.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ContactDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const contactEntity = useAppSelector(state => state.contact.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="contactDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.contact.detail.title">Contact</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{contactEntity.id}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="hcpNphiesPortalApp.contact.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{contactEntity.phone}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="hcpNphiesPortalApp.contact.email">Email</Translate>
            </span>
          </dt>
          <dd>{contactEntity.email}</dd>
          <dt>
            <span id="mobile">
              <Translate contentKey="hcpNphiesPortalApp.contact.mobile">Mobile</Translate>
            </span>
          </dt>
          <dd>{contactEntity.mobile}</dd>
          <dt>
            <span id="url">
              <Translate contentKey="hcpNphiesPortalApp.contact.url">Url</Translate>
            </span>
          </dt>
          <dd>{contactEntity.url}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.contact.name">Name</Translate>
          </dt>
          <dd>{contactEntity.name ? contactEntity.name.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.contact.organization">Organization</Translate>
          </dt>
          <dd>{contactEntity.organization ? contactEntity.organization.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/contact" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/contact/${contactEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContactDetail;
